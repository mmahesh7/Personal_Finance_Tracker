package com.financetracker.managers;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import com.financetracker.exceptions.DataFileException;
import com.financetracker.models.Transaction;

public class FileManager {
    
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String BACKUP_FILE = "transactions_backup.txt";

    //Creating or Updating the BACKUP_FILE
    private void createBackup() throws IOException{
        File original = new File(TRANSACTIONS_FILE);
        if(original.exists()) {
            try (FileInputStream fis = new FileInputStream(TRANSACTIONS_FILE);
            FileOutputStream fos = new FileOutputStream(BACKUP_FILE)) {
                byte[] buffer = new byte[1024];
                int length;
                while((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            } 
        }
    }

    //Saving the transactions
    public void saveTransactions(List<Transaction> transactions) throws DataFileException {
        try {
            createBackup();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
                for(Transaction transaction : transactions){
                    writer.write(transaction.toFileFormat());
                    writer.newLine();
                }
                System.out.println("Data saved successfully");
            }
        } catch(IOException e) {
            throw new DataFileException("Error creating backup file", e);
        }
    }

    //Load transactions
    public List<Transaction> loadTransactions() throws DataFileException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);

        if(!file.exists()) {
            System.out.println("No existing data found. Starting fresh");
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while((line = reader.readLine()) != null) {
                try {
                    Transaction transaction = Transaction.fromFileFormat(line);
                    transactions.add(transaction);
                } catch(Exception e) {
                    System.err.println("Skipping invalid transaction: "+line);
                }
            }
            System.out.println("Loaded " + transactions.size() + " transactions.");
        } catch (IOException e) {
            throw new DataFileException("Failed to load transactions", e);
        }
        return transactions;
    }
}
