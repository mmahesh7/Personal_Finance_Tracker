package com.financetracker.main;

import com.financetracker.models.*;

public class TransactionTester {
    public static void main(String[] args) {
        System.out.println("=== Transaction Class Tests ===");

        // Test 1: Valid Income transaction
        Transaction t1 = new Income(500.0, "Salary", "Job", "Company XYZ");
        System.out.println("Created: " + t1);

        // Test 2: toFileFormat and fromFileFormat
        String line = t1.toFileFormat();
        System.out.println("File Format: " + line);

        Transaction t2 = Transaction.fromFileFormat(line);
        System.out.println("Parsed: " + t2);

        // Test 3: Valid Expense transaction
        Transaction e1 = new Expense(200.0, "Groceries", "Food & Dining", true);
        System.out.println("Created: " + e1);

        String expenseLine = e1.toFileFormat();
        System.out.println("File Format: " + expenseLine);

        Transaction e2 = Transaction.fromFileFormat(expenseLine);
        System.out.println("Parsed: " + e2);

        // Test 4: Invalid Income amount
        try {
            new Income(0, "Test Income", "Other", "Misc");
            System.out.println("FAILED: Zero amount not caught");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASSED: Zero amount caught");
        }

        // Test 5: Invalid Expense amount
        try {
            new Expense(-100, "Test Expense", "Bills", false);
            System.out.println("FAILED: Negative amount not caught");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASSED: Negative amount caught");
        }
    }
}
