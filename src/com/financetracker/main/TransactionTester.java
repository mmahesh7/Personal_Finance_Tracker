package com.financetracker.main;

import com.financetracker.models.Transaction;

public class TransactionTester {
    public static void main(String[] args) {
        System.out.println("=== Transaction Class Tests ===");

        // Test 1: Valid transaction
        Transaction t1 = new Transaction(500.0, "Salary", "INCOME");
        System.out.println("Created: " + t1);

        // Test 2: toFileFormat and fromFileFormat
        String line = t1.toFileFormat();
        System.out.println("File Format: " + line);

        Transaction t2 = Transaction.fromFileFormat(line);
        System.out.println("Parsed: " + t2);

        // Test 3: Invalid type
        try {
            new Transaction(100, "Test", "BONUS");
            System.out.println("FAILED: Invalid type not caught");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Invalid type caught");
        }

        // Test 4: Invalid amount
        try {
            new Transaction(0, "Test", "INCOME");
            System.out.println("FAILED: Zero amount not caught");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Zero amount caught");
        }
    }
}
