package com.financetracker.managers;

import com.financetracker.models.Transaction;
import com.financetracker.exceptions.DataFileException;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String BACKUP_FILE = "transactions_backup.txt";

    /**
     * Saves transactions atomically:
     *  - writes to a temporary file
     *  - if successful, replaces the original file
     *  - keeps a simple backup copy of the previous file
     */
    public void saveTransactions(List<Transaction> transactions) throws DataFileException {
        File temp = new File(TRANSACTIONS_FILE + ".tmp");
        File dest = new File(TRANSACTIONS_FILE);
        File backup = new File(BACKUP_FILE);

        // Write to temp file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
            for (Transaction t : transactions) {
                // Enhanced validation - let Transaction class handle its own validation
                if (t == null) {
                    System.err.println("Warning: Skipping null transaction");
                    continue;
                }
                if (t.getId() <= 0) {
                    System.err.println("Warning: Skipping transaction with invalid ID: " + t.getId());
                    continue;
                }
                if (t.getAmount() <= 0) {
                    System.err.println("Warning: Skipping transaction with invalid amount: " + t.getAmount());
                    continue;
                }
                if (t.getDescription() == null || t.getDescription().trim().isEmpty()) {
                    System.err.println("Warning: Skipping transaction with invalid description");
                    continue;
                }
                writer.write(t.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataFileException("Failed to write transactions to temp file", e);
        }

        // Create a backup of current file (if exists)
        try {
            if (dest.exists()) {
                Files.copy(dest.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            // Backup failing should not prevent save, but inform user
            System.err.println("Warning: Could not create backup: " + e.getMessage());
        }

        // Replace original with temp (attempt atomic move; fallback to rename)
        try {
            try {
                Files.move(temp.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                // fallback
                Files.move(temp.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new DataFileException("Failed to replace transactions file", e);
        }

        System.out.println("Data saved successfully");
    }

    
    // Loads transactions from the file.
    // Skips invalid lines but continues reading other lines.
    public List<Transaction> loadTransactions() throws DataFileException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);

        if (!file.exists()) {
            System.out.println("No existing data found. Starting fresh");
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNo = 0;
            int validTransactions = 0;
            int invalidTransactions = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                
                try {
                    Transaction t = Transaction.fromFileFormat(line);
                    transactions.add(t);
                    validTransactions++;
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid transaction (line " + lineNo + "): " + line);
                    System.err.println("  Reason: " + e.getMessage());
                    invalidTransactions++;
                }
            }
            
            System.out.printf("Loaded %d valid transactions", validTransactions);
            if (invalidTransactions > 0) {
                System.out.printf(" (%d invalid lines skipped)", invalidTransactions);
            }
            System.out.println();
            
        } catch (IOException e) {
            throw new DataFileException("Failed to read transactions file", e);
        }

        if (transactions.isEmpty() && new File(TRANSACTIONS_FILE).length() > 0) {
            System.out.println("Warning: File found but no valid transactions loaded.");
        }
        
        return transactions;
    }

    /** Simple restore from backup method (optional, not used automatically) */
    public void restoreFromBackup() throws DataFileException {
        File backup = new File(BACKUP_FILE);
        File dest = new File(TRANSACTIONS_FILE);
        if (!backup.exists()) throw new DataFileException("Backup file not found");

        try {
            Files.copy(backup.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new DataFileException("Failed to restore from backup", e);
        }
    }
}

