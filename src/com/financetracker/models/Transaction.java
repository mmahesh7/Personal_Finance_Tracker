package com.financetracker.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Transaction {
    private static int nextId = 1001;

    protected int id;
    protected double amount;
    protected String description;
    protected LocalDate date;
    protected String category;

    // File date format: ISO (yyyy-MM-dd)
    protected static final DateTimeFormatter FILE_DATE = DateTimeFormatter.ISO_DATE;
    // Display date format: dd-MM-yyyy
    protected static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Constructor for new transactions
    protected Transaction(double amount, String description, String category) {
        validateAmount(amount);
        validateDescription(description);
        
        this.id = nextId++;
        this.amount = amount;
        this.description = description.trim();
        this.category = validateAndSetCategory(category);
        this.date = LocalDate.now();
    }

    // Constructor for loading from file
    protected Transaction(int id, double amount, String description, String category, LocalDate date) {
        validateAmount(amount);
        validateDescription(description);
        
        this.id = id;
        this.amount = amount;
        this.description = description.trim();
        this.category = validateAndSetCategory(category);
        this.date = date;

        if (id >= nextId) nextId = id + 1;
    }

    // Abstract methods - each transaction type implements differently
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
        validateAmount(amount);
        this.amount = amount;
    }
    
    public void setDescription(String description) {
        validateDescription(description);
        this.description = description.trim();
    }
    
    public void setCategory(String category) {
        this.category = validateAndSetCategory(category);
    }
    
    // Validation helpers
    private void validateAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be empty");
    }

    private String validateAndSetCategory(String category) {
        // Category can be any non-empty string, defaults to "Other"
        return (category != null && !category.trim().isEmpty()) ? category.trim() : "Other";
    }

    // Enhanced file serialization with additional fields
    public String toFileFormat() {
        // Replace any '|' in description to avoid delimiter conflicts
        String safeDesc = description.replace("|", "/");
        return String.format(Locale.US, "%d|%s|%.2f|%s|%s|%s|%s",
                id,
                getType(),
                amount,
                safeDesc,
                category,
                date.format(FILE_DATE),
                getAdditionalFields()); // Let subclasses add their specific fields
    }
    
    // Abstract method for subclasses to provide additional fields
    protected abstract String getAdditionalFields();

    // Parse from file-format line with backward compatibility
    public static Transaction fromFileFormat(String line) {
        if (line == null) throw new IllegalArgumentException("Line is null");

        String[] parts = line.split("\\|", -1);
        
        // Support both old (6 parts) and new (7+ parts) formats
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid transaction file format");
        }

        try {
            int id = Integer.parseInt(parts[0].trim());
            String type = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            String description = parts[3].trim();
            String category = parts[4].trim();
            LocalDate date = LocalDate.parse(parts[5].trim(), FILE_DATE);
            
            // Additional fields for enhanced format
            String additionalFields = parts.length > 6 ? parts[6] : "";

            switch (type.toUpperCase()) {
                case "INCOME":
                    return createIncomeFromFile(id, amount, description, category, date, additionalFields);
                case "EXPENSE":
                    return createExpenseFromFile(id, amount, description, category, date, additionalFields);
                default:
                    throw new IllegalArgumentException("Unknown transaction type: " + type);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse transaction: " + line, e);
        }
    }
    
    // Helper methods for creating specific transaction types from file
    private static Income createIncomeFromFile(int id, double amount, String description, 
                                             String category, LocalDate date, String additionalFields) {
        if (additionalFields.isEmpty()) {
            return new Income(id, amount, description, category, date);
        } else {
            // Parse additional fields for enhanced Income
            return new Income(id, amount, description, category, date, additionalFields);
        }
    }
    
    private static Expense createExpenseFromFile(int id, double amount, String description,
                                               String category, LocalDate date, String additionalFields) {
        if (additionalFields.isEmpty()) {
            return new Expense(id, amount, description, category, date);
        } else {
            // Parse additional fields: "isEssential,paymentMethod"
            String[] fields = additionalFields.split(",", 2);
            boolean isEssential = fields.length > 0 ? Boolean.parseBoolean(fields[0]) : true;
            String paymentMethod = fields.length > 1 ? fields[1] : "Unknown";
            return new Expense(id, amount, description, category, date, isEssential, paymentMethod);
        }
    }

    // User-friendly console representation
    @Override
    public String toString() {
        return String.format("ID: %d | %s%.2f | %s | [%s] | %s", 
                           id,
                           getDisplaySymbol(),
                           amount, 
                           description,
                           category,
                           date.format(DISPLAY_DATE));
    }
}