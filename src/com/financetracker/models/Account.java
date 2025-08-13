package com.financetracker.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.financetracker.exceptions.DataFileException;
import com.financetracker.exceptions.InsufficientFundsException;
import com.financetracker.exceptions.InvalidTransactionException;
import com.financetracker.managers.FileManager;

public class Account {
    private String accountName;
    private double balance;
    private List<Transaction> transactions;
    private FileManager fileManager;

    public Account(String accountName) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        this.accountName = accountName;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.fileManager = new FileManager();
        loadData();
    }

    public String getAccountName() { return accountName; }
    public double getBalance() { return balance; }
    public int getTransactionCount() { return transactions.size(); }

    // Quick add (defaults for category & source)
    // public void addIncome(double amount, String description) throws InvalidTransactionException {
    //     addIncome(amount, description, "Other", "Other");
    // }

    // Full detail add (with category & source)
    public void addIncome(double amount, String description, String category, String source)
            throws InvalidTransactionException {
        try {
            Income income = new Income(amount, description, category, source);
            transactions.add(income);
            balance += amount;
            saveData();
            System.out.printf(Locale.US, "Income added: %.2f. New balance: %.2f%n", amount, balance);
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionException("Invalid income data: " + e.getMessage());
        }
    }

    // Quick add (defaults for category & essential flag)
    // public void addExpense(double amount, String description) throws InvalidTransactionException, InsufficientFundsException {
    //     addExpense(amount, description, "Other", false);
    // }

    // Full detail add (with category & essential flag)
    public void addExpense(double amount, String description, String category, boolean isEssential)
            throws InvalidTransactionException, InsufficientFundsException {
        try {
            if (balance < amount) {
                throw new InsufficientFundsException("Insufficient balance for expense.");
            }
            Expense expense = new Expense(amount, description, category, isEssential);
            transactions.add(expense);
            balance -= amount;
            saveData();
            System.out.printf(Locale.US, "Expense added: %.2f. New balance: %.2f%n", amount, balance);
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionException("Invalid expense data: " + e.getMessage());
        }
    }

    public void displayTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\n=== TRANSACTION HISTORY ===");
        for (Transaction t : transactions) {
            System.out.println(t); // Transaction.toString() handles display formatting
        }
        System.out.printf(Locale.US, "\nCurrent Balance: %.2f%n", balance);
    }

    public void displaySummary() {
        System.out.println("\n=== ACCOUNT SUMMARY ===");
        System.out.printf(Locale.US, "Account: %s%n", accountName);
        System.out.printf(Locale.US, "Total Income: %.2f%n", getTotalIncome());
        System.out.printf(Locale.US, "Total Expenses: %.2f%n", getTotalExpenses());
        System.out.printf(Locale.US, "Net Balance: %.2f%n", getTotalIncome() - getTotalExpenses());
    }

    public double getTotalIncome() {
        // WHY use streams? Modern, readable way to filter and sum
        return transactions.stream()
                .filter(t -> t.getType().equals("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> t.getType().equals("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // public double getTotalIncome() {
    //     return transactions.stream()
    //             .filter(t -> "INCOME".equals(t.getType()))
    //             .mapToDouble(Transaction::getAmount)
    //             .sum();
    // }

    // public double getTotalExpenses() {
    //     return transactions.stream()
    //             .filter(t -> "EXPENSE".equals(t.getType()))
    //             .mapToDouble(Transaction::getAmount)
    //             .sum();
    // }

    private void saveData() {
        try {
            fileManager.saveTransactions(transactions);
        } catch (DataFileException e) {
            System.err.println("Warning: Could not save data - " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            transactions = fileManager.loadTransactions();
            recalcBalance();
        } catch (DataFileException e) {
            System.err.println("Warning: Could not load data - " + e.getMessage());
            transactions = new ArrayList<>();
        }
    }

    private void recalcBalance() {
        double total = 0.0;
        for (Transaction t : transactions) {
            total += t.getBalanceImpact(); // Income positive, Expense negative
        }
        balance = total;
    }

    // Optional helper used by some tests
    // public void recalculateBalance() {
    //     recalcBalance();
    // }

    public String getStatistics() {
        return String.format(Locale.US,
                "Account: %s | Income: %.2f | Expenses: %.2f | Net: %.2f",
                accountName, getTotalIncome(), getTotalExpenses(), (getTotalIncome() - getTotalExpenses()));
    }
}
