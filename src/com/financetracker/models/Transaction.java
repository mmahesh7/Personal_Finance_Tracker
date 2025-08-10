package com.financetracker.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Transaction {

    // WHY static? We want a unique ID for each transaction across ALL instances
    // WHY start at 1001? Makes it look more professional than starting at 1
    private static int nextId = 1;

     // WHY private? Encapsulation - we control how these are accessed/modified
    private int id;
    private double amount;
    private String description;
    private String type; // to check whether its a income or expense
    private LocalDate date; //At what date & time transaction happened
    
    //Constructors
    //Construnctor for adding new transaction(user creates them)
    public Transaction(double amount, String description, String type) {
        this.id = nextId++;//For every new transaction the id increments by 1
        this.amount = amount;
        this.description = description;
        this.type = type.toUpperCase();//to prevent "income" vs "INCOME" issues
        this.date = LocalDate.now();
    }
    
    //Using a set to ensure that user does not enter an existing id ie duplicate id
    private static Set<Integer> usedIds = new HashSet<>();

    //Constructor for loading a transaction(System create them given id)
    public Transaction(int id, double amount, String desc, String type, LocalDate date) {
        if (usedIds.contains(id)) {
            throw new IllegalArgumentException("Duplicate ID: " + id);
        }
        usedIds.add(id);
        this.id = id;
        this.amount = amount;
        this.description = desc;
        this.type = type.toUpperCase();
        this.date = date;

        //Why? When loading from file, we need to make sure future
        //transactions don't have duplicate IDs
        if(id >= nextId) {
            nextId = id + 1;
        }
    }

    // WHY getters? Encapsulation - we provide controlled access to private fields
    //Getters : 
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public LocalDate getDate() { return date; }

    //Setters:
    public void setAmount(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }
        this.amount = amount;
    }

    public void setDescription(String description) {
        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }
        this.description = description.trim();//trim() to remove extra spaces
    }

    public void setType(String type) {
        if(!type.equalsIgnoreCase("INCOME") && !type.equalsIgnoreCase("EXPENSE")) {
            throw new IllegalArgumentException("Type must be INCOME or EXPENSE");
        }
        this.type = type.toUpperCase();
    }

    // Convert transaction to string format for saving to file
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return id + "," + amount + "," + description + "," + type + "," + date.format(formatter);
    }

    // Create transaction from file string (static factory method)
    public static Transaction fromFileFormat(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid file format");
        }
        // WHY parse each part? Convert strings back to proper data types
        int id = Integer.parseInt(parts[0]);
        double amount = Double.parseDouble(parts[1]);
        String description = parts[2];
        String type = parts[3];
        LocalDate date = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return new Transaction(id, amount, description, type, date);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // WHY formatted string? Makes output readable
        return String.format("ID: %d | %s | %.2f | %s | %s", id, type, amount, description, date.format(formatter));
    }
}

