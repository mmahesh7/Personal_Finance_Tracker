package com.financetracker.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.financetracker.models.Account;

public class FinanceTrackerApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Account account;

    public static void main(String[] args) {
        System.out.println("=== PERSONAL FINANCE TRACKER ===");
        System.out.print("Enter your account name: ");
        String accountName = scanner.nextLine();
        
        account = new Account(accountName);
        System.out.println("Welcome, " + accountName + "!");

        boolean running = true;
        while(running) {
            try {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");
                switch (choice) {
                    case 1: addIncomeBasic(); break;
                    case 2: addIncomeDetailed(); break;
                    case 3: addExpenseBasic(); break;
                    case 4: addExpenseDetailed(); break;
                    case 5: account.displayTransactions(); break;
                    case 6: account.displaySummary(); break;
                    case 7: 
                        System.out.println("Thank you for using Finance Tracker!");
                        running = false;
                        break;
                    default: 
                        System.out.println("Invalid choice. Please try again.");
                }

                if(running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            } catch(Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear any problematic input
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add Income (Basic)");
        System.out.println("2. Add Income (Detailed)");
        System.out.println("3. Add Expense (Basic)");
        System.out.println("4. Add Expense (Detailed)");
        System.out.println("5. View Transactions");
        System.out.println("6. View Account Summary");
        System.out.println("7. Exit");
    }

    private static void addIncomeBasic() {
        System.out.println("\n--- ADD INCOME (BASIC) ---");
        double amount = getDoubleInput("Enter income amount: ");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        try {
            account.addIncome(amount, description);
            System.out.println("Income added successfully.");
        } catch (Exception e) {
            System.err.println("Failed to add income: " + e.getMessage());
        }
    }

    private static void addIncomeDetailed() {
        System.out.println("\n--- ADD INCOME (DETAILED) ---");
        double amount = getDoubleInput("Enter income amount: ");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter category (or press Enter for 'Other'): ");
        String category = scanner.nextLine();
        if (category.trim().isEmpty()) category = "Other";
        System.out.print("Enter source (or press Enter for 'Other'): ");
        String source = scanner.nextLine();
        if (source.trim().isEmpty()) source = "Other";
        
        try {
            account.addIncome(amount, description, category, source);
            System.out.println("Detailed income added successfully.");
        } catch (Exception e) {
            System.err.println("Failed to add income: " + e.getMessage());
        }
    }

    private static void addExpenseBasic() {
        System.out.println("\n--- ADD EXPENSE (BASIC) ---");
        double amount = getDoubleInput("Enter expense amount: ");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        try {
            account.addExpense(amount, description);
            System.out.println("Expense added successfully.");
        } catch (Exception e) {
            System.err.println("Failed to add expense: " + e.getMessage());
        }
    }

    private static void addExpenseDetailed() {
        System.out.println("\n--- ADD EXPENSE (DETAILED) ---");
        double amount = getDoubleInput("Enter expense amount: ");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter category (or press Enter for 'Other'): ");
        String category = scanner.nextLine();
        if (category.trim().isEmpty()) category = "Other";
        
        boolean isEssential = false;
        System.out.print("Is this expense essential? (y/n): ");
        String essentialInput = scanner.nextLine().toLowerCase();
        if (essentialInput.startsWith("y")) {
            isEssential = true;
        }
        
        System.out.print("Enter payment method (or press Enter for 'Unknown'): ");
        String paymentMethod = scanner.nextLine();
        if (paymentMethod.trim().isEmpty()) paymentMethod = "Unknown";
        
        try {
            account.addExpense(amount, description, category, isEssential, paymentMethod);
            System.out.println("Detailed expense added successfully.");
        } catch (Exception e) {
            System.err.println("Failed to add expense: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (value <= 0) {
                    System.out.println("Amount must be positive.");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid amount.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}