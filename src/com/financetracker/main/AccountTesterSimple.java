package com.financetracker.main;

import com.financetracker.models.Account;
import com.financetracker.exceptions.InsufficientFundsException;
import com.financetracker.exceptions.InvalidTransactionException;

import java.io.File;

public class AccountTesterSimple {
    public static void main(String[] args) {
        System.out.println("=== Account Class Tests ===");
        cleanup();

        Account account = new Account("Local Test User");

        // Test 1: Add income
        try {
            account.addIncome(1000, "Salary");
            System.out.println("Balance after income: " + account.getBalance());
        } catch (InvalidTransactionException e) {
            System.out.println("FAILED: Income error - " + e.getMessage());
        }

        // Test 2: Add expense
        try {
            account.addExpense(300, "Rent");
            System.out.println("Balance after expense: " + account.getBalance());
        } catch (InvalidTransactionException | InsufficientFundsException e) {
            System.out.println("FAILED: Expense error - " + e.getMessage());
        }

        // Test 3: Totals
        System.out.println("Total Income: " + account.getTotalIncome());
        System.out.println("Total Expenses: " + account.getTotalExpenses());

        // Test 4: Insufficient funds
        try {
            account.addExpense(5000, "Overdraft");
            System.out.println("FAILED: Overdraft allowed!");
        } catch (InsufficientFundsException e) {
            System.out.println("PASSED: Overdraft blocked");
        } catch (InvalidTransactionException e) {
            System.out.println("FAILED: Wrong exception type");
        }

        // Test 5: Display
        account.displayTransactions();

        System.out.println(account.getStatistics());
    }

    private static void cleanup() {
        new File("transactions.txt").delete();
        new File("transactions_backup.txt").delete();
    }
}
