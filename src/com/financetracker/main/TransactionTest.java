// package com.financetracker.main;

// import java.time.LocalDate;

// import com.financetracker.models.Transaction;

// public class TransactionTest {
//     public static void main(String[] args) {
//         Transaction t1 = new Transaction(1000, "Mobile Recharge", "Expense");
//         Transaction t2 = new Transaction(5, 500, "Pocket Money", "Income", LocalDate.of(2025, 8, 10));
//         Transaction t3 = new Transaction(1000, "Mobile Recharge", "Expense");
//         // Transaction t4 = new Transaction(5, 500, "Pocket Money", "Income", LocalDate.of(2025, 8, 10));


//         System.out.println(t1);
//         System.out.println(t2);
//         System.out.println(t3);
//         // System.out.println(t4);
//     }
// }

package com.financetracker.main;

import java.time.LocalDate;

import com.financetracker.models.Transaction;


public class TransactionTest {
    public static void main(String[] args) {
        System.out.println("=== TESTING TRANSACTION CLASS ===\n");
        
        // Test 1: Create new transactions
        testTransactionCreation();
        
        // Test 2: Test file format conversion
        testFileFormatConversion();
        
        // Test 3: Test validation
        testValidation();
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }
    
    private static void testTransactionCreation() {
        System.out.println("1. Testing Transaction Creation:");

        Transaction t1 = new Transaction(1000, "Mobile Recharge", "Expense");
        Transaction t2 = new Transaction(5, 500, "Pocket Money", "Income", LocalDate.of(2025, 8, 10));
        Transaction t3 = new Transaction(1000, "Mobile Recharge", "Expense");
        // Transaction t4 = new Transaction(5, 500, "Pocket Money", "Income", LocalDate.of(2025, 8, 10));
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);

        Transaction income = new Transaction(2500.00, "Salary", "income");
        Transaction expense = new Transaction(150.75, "Groceries", "expense");
        
        System.out.println("Income: " + income);
        System.out.println("Expense: " + expense);
        
        System.out.println(" Transaction creation works!\n");
    }
    
    private static void testFileFormatConversion() {
        System.out.println("2. Testing File Format Conversion:");
        
        Transaction original = new Transaction(100.50, "Coffee", "expense");
        String fileFormat = original.toFileFormat();
        System.out.println("File format: " + fileFormat);
        
        Transaction restored = Transaction.fromFileFormat(fileFormat);
        System.out.println("Restored: " + restored);
        
        // Verify data matches
        boolean matches = original.getId() == restored.getId() &&
                         original.getAmount() == restored.getAmount() &&
                         original.getDescription().equals(restored.getDescription()) &&
                         original.getType().equals(restored.getType()) &&
                         original.getDate().equals(restored.getDate());
        
        System.out.println(matches ? " File conversion works!" : " File conversion failed!");
        System.out.println();
    }
    
    private static void testValidation() {
        System.out.println("3. Testing Validation:");
        
        try {
            Transaction invalid = new Transaction(-100, "Invalid", "expense");
            System.out.println(" Should have caught negative amount!");
        } catch (IllegalArgumentException e) {
            System.out.println(" Caught negative amount: " + e.getMessage());
        }
        
        try {
            Transaction valid = new Transaction(50, "", "income");
            System.out.println(" Should have caught empty description!");
        } catch (IllegalArgumentException e) {
            System.out.println(" Caught empty description: " + e.getMessage());
        }
        
        try {
            Transaction valid = new Transaction(50, "Test", "INVALID_TYPE");
            System.out.println(" Should have caught invalid type!");
        } catch (IllegalArgumentException e) {
            System.out.println(" Caught invalid type: " + e.getMessage());
        }
        
        System.out.println(" Validation works!\n");
    }
}