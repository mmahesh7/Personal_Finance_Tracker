package com.financetracker.main;

import com.financetracker.managers.FileManager;
import com.financetracker.models.Transaction;
import com.financetracker.exceptions.DataFileException;

import java.io.File;
import java.util.*;

public class FileManagerTester {
    public static void main(String[] args) {
        System.out.println("=== FileManager Class Tests ===");

        FileManager fm = new FileManager();
        // cleanup();

        // Test 1: Save and load
        List<Transaction> list = Arrays.asList(
                new Transaction(100, "Test Income", "INCOME"),
                new Transaction(50, "Test Expense", "EXPENSE")
        );
        try {
            fm.saveTransactions(list);
            System.out.println("PASSED: Save successful");
        } catch (DataFileException e) {
            System.out.println("FAILED: Save error - " + e.getMessage());
        }

        try {
            List<Transaction> loaded = fm.loadTransactions();
            System.out.println("Loaded transactions: " + loaded.size());
            for (Transaction t : loaded) {
                System.out.println(t);
            }
        } catch (DataFileException e) {
            System.out.println("FAILED: Load error - " + e.getMessage());
        }

        // Test 2: Restore from backup
        try {
            fm.restoreFromBackup();
            System.out.println("PASSED: Restore from backup");
        } catch (DataFileException e) {
            System.out.println("FAILED: Restore error - " + e.getMessage());
        }
    }

    private static void cleanup() {
        new File("transactions.txt").delete();
        new File("transactions_backup.txt").delete();
    }
}
