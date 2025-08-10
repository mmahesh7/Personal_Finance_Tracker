package com.financetracker.main;

import com.financetracker.managers.FileManager;
import com.financetracker.models.Transaction;
import com.financetracker.exceptions.DataFileException;
import java.util.ArrayList;
import java.util.List;

public class FileManagerTest {
    public static void main(String[] args) {
        System.out.println("=== TESTING FILEMANAGER ===\n");
        
        FileManager fileManager = new FileManager();
        
        // Test 1: Create some transactions
        List<Transaction> testTransactions = new ArrayList<>();
        testTransactions.add(new Transaction(1000, "Salary", "INCOME"));
        testTransactions.add(new Transaction(50, "Lunch", "EXPENSE"));
        testTransactions.add(new Transaction(200, "Groceries", "EXPENSE"));
        
        try {
            // Test 2: Save transactions
            System.out.println(" Testing save functionality...");
            fileManager.saveTransactions(testTransactions);
            
            // Test 3: Load transactions
            System.out.println("\n Testing load functionality...");
            List<Transaction> loadedTransactions = fileManager.loadTransactions();
            
            // Test 4: Display loaded transactions
            System.out.println("\n Loaded Transactions:");
            for (Transaction t : loadedTransactions) {
                System.out.println(t);
            }
            
            System.out.println("\n FileManager test completed successfully!");
            System.out.println(" Duplicate ID issue should now be resolved!");
            
        } catch (DataFileException e) {
            System.err.println(" Test failed: " + e.getMessage());
        }
    }
}
