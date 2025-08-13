package com.financetracker.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Transaction {
    private static int nextId = 1001;

    protected int id;
    protected double amount;
    protected String description;
    // protected String type; // "INCOME" or "EXPENSE"
    protected LocalDate date;
    protected String category;//New

    // File date format: ISO (yyyy-MM-dd)
    protected static final DateTimeFormatter FILE_DATE = DateTimeFormatter.ISO_DATE;
    // Display date format: dd-MM-yyyy
    protected static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Constructor for new transactions
    protected Transaction(double amount, String description, String category) {
        validateAmount(amount);
        validateDescription(description);
        setCategory(category);

        this.id = nextId++;
        this.amount = amount;
        this.description = description.trim();
        this.category = category != null ? category : "Other";
        this.date = LocalDate.now();
    }

    // Constructor for loading from file
    protected Transaction(int id, double amount, String description, String category, LocalDate date) {
        validateAmount(amount);
        validateDescription(description);
        validateCategory(category);

        this.id = id;
        this.amount = amount;
        this.description = description.trim();
        // this.category = category != null ? category : "Other";
        this.date = date;

        if (id >= nextId) nextId = id + 1;
    }

    
    // Abstract methods - WHY? Each transaction type implements differently
    public abstract String getType();
    public abstract double getBalanceImpact(); // +ve for income, -ve for expense
    public abstract String getDisplaySymbol(); // + or - for display
    
    // Common Getters
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }

    // Common setters with validation
    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }
    
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description.trim();
    }
    
    public void setCategory(String category) {
        this.category = category != null && !category.trim().isEmpty() 
                       ? category.trim() : "Other";
    }
    
    
    // Validation helpers
    private void validateAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be empty");
    }

    private void validateCategory(String type) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        String t = type.toUpperCase();
        if (!t.equals("INCOME") && !t.equals("EXPENSE") && !t.equals(null))
            throw new IllegalArgumentException("Type must be INCOME or EXPENSE");
    }


    // File serialization: consistent, Locale.US, pipe delimiter to avoid comma issues
    public String toFileFormat() {
        // Replace any '|' in description to avoid delimiter conflicts
        String safeDesc = description.replace("|", "/");
        return String.format(Locale.US, "%d|%s|%.2f|%s|%s|%s",
                id,
                getType(),
                amount,
                safeDesc,
                category,
                date.format(FILE_DATE)); // yyyy-MM-dd
    }

    // Parse from file-format line
    public static Transaction fromFileFormat(String line) {
        if (line == null) throw new IllegalArgumentException("Line is null");

        String[] parts = line.split("\\|", -1); // preserve empty trailing fields
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid transaction file format");
        }

        try {
            int id = Integer.parseInt(parts[0].trim());
            String type = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            String description = parts[3].trim();
            String category = parts[4].trim();
            LocalDate date = LocalDate.parse(parts[5].trim(), FILE_DATE);

            switch (type.toUpperCase()) {
                case "INCOME":
                    return new Income(id, amount, description, category, date);
                case "EXPENSE":
                    return new Expense(id, amount, description, category, date);
                default:
                    throw new IllegalArgumentException("Unknown transaction type: " + type);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse transaction: " + line, e);
        }
    }

    // user-friendly console representation
    @Override
    public String toString() {
        // return String.format(Locale.US, "ID: %d | %s | %.2f | %s | %s",
        //         id,
        //         type,
        //         amount,
        //         description,
        //         date.format(DISPLAY_DATE)); // dd-MM-yyyy for display

        return String.format("ID: %d | %s%.2f | %s | [%s] | %s", 
                           id,
                           getDisplaySymbol(),
                           amount, 
                           description,
                           category,
                           date.format(DISPLAY_DATE));//dd-MM-yyyy for display
    }
}
