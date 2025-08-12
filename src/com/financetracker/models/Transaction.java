package com.financetracker.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transaction {
    private static int nextId = 1001;

    private int id;
    private double amount;
    private String description;
    private String type; // "INCOME" or "EXPENSE"
    private LocalDate date;

    // File date format: ISO (yyyy-MM-dd)
    private static final DateTimeFormatter FILE_DATE = DateTimeFormatter.ISO_DATE;
    // Display date format: dd-MM-yyyy
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Constructor for new transactions
    public Transaction(double amount, String description, String type) {
        validateAmount(amount);
        validateDescription(description);
        validateType(type);

        this.id = nextId++;
        this.amount = amount;
        this.description = description.trim();
        this.type = type.toUpperCase();
        this.date = LocalDate.now();
    }

    // Constructor for loading from file
    public Transaction(int id, double amount, String description, String type, LocalDate date) {
        validateAmount(amount);
        validateDescription(description);
        validateType(type);

        this.id = id;
        this.amount = amount;
        this.description = description.trim();
        this.type = type.toUpperCase();
        this.date = date;

        if (id >= nextId) nextId = id + 1;
    }

    // Validation helpers
    private void validateAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be empty");
    }

    private void validateType(String type) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        String t = type.toUpperCase();
        if (!t.equals("INCOME") && !t.equals("EXPENSE"))
            throw new IllegalArgumentException("Type must be INCOME or EXPENSE");
    }

    // Getters
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public LocalDate getDate() { return date; }

    // File serialization: consistent, Locale.US, pipe delimiter to avoid comma issues
    public String toFileFormat() {
        // Replace any '|' in description to avoid delimiter conflicts
        String safeDesc = description.replace("|", "/");
        return String.format(Locale.US, "%d|%s|%.2f|%s|%s",
                id,
                type,
                amount,
                safeDesc,
                date.format(FILE_DATE)); // yyyy-MM-dd
    }

    // Parse from file-format line
    public static Transaction fromFileFormat(String line) {
        if (line == null) throw new IllegalArgumentException("Line is null");

        String[] parts = line.split("\\|", -1); // preserve empty trailing fields
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid transaction file format");
        }

        try {
            int id = Integer.parseInt(parts[0].trim());
            String type = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            String description = parts[3].trim();
            LocalDate date = LocalDate.parse(parts[4].trim(), FILE_DATE);

            return new Transaction(id, amount, description, type, date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse transaction: " + line, e);
        }
    }

    // Human-friendly console representation
    @Override
    public String toString() {
        return String.format(Locale.US, "ID: %d | %s | %.2f | %s | %s",
                id,
                type,
                amount,
                description,
                date.format(DISPLAY_DATE)); // dd-MM-yyyy for display
    }
}
