package com.financetracker.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.financetracker.models.Account;

public class FinanceTrackerApp {
    private static Scanner scanner = new Scanner(System.in);

    public static Account account;

    public static void main(String[] args) {
        System.out.println("=== PERSONAL FINANCE TRACKER ===");
        System.out.print("Enter your account name: ");
        String accountName = scanner.nextLine();
        
        // WHY create account here? Need user's name first
        account = new Account(accountName);
        System.out.println("Welcome, " + accountName + "!");

        boolean running = true;
        while(running) {
            try {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");
                switch (choice) {
                    case 1: addIncome(); break;
                    case 2: addExpense(); break;
                    case 3: account.displayTransactions(); break;
                    case 4: account.displaySummary(); break;
                    case 5: System.out.println("Thank you for using Finance Tracker!");
                            running =false;
                            break;
                    default: System.out.println("Invalid choice. Please try again.");
                }

                if(running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine(); // WHY? Pause so user can read output
                }
            } catch(Exception e) {
                System.err.println("An error occured: " + e.getMessage());
                scanner.nextLine(); // WHY? Clear any problematic input
            }
        }
        scanner.close();
    }

    private static int getIntInput(String prompt) {
        while (true) { // WHY infinite loop? Keep trying until valid input
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // WHY? Consume the newline character
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // WHY? Clear the invalid input
            }
        }
    }
    
    
    private static void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Transactions");
        System.out.println("4. View Account Summary");
        System.out.println("5. Exit");
    }

    
    private static void addIncome() {
        System.out.println("\n--- ADD INCOME ---");
        double amount = getDoubleInput("Enter income amount: "); // Validated input
        System.out.print("Enter description: ");
        String description = scanner.nextLine(); // Can be any text
        
        try {
            account.addIncome(amount, description);
            System.out.println("Income added successfully.");
        } catch (Exception e) {
            System.err.println("Failed to add income: " + e.getMessage());
        }
    }

    
    private static void addExpense() {
        System.out.println("\n--- ADD EXPENSE ---");
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

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (value <= 0) {
                    System.out.println("Amount must be positive.");
                    continue; // WHY continue? Try again with same loop
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid amount.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
