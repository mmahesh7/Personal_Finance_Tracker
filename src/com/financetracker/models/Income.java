package com.financetracker.models;
import java.time.LocalDate;

public class Income extends Transaction {
    private String source; // NEW: Track income sources

    // Constructor for new income
    public Income(double amount, String description, String category, String source) {
        super(amount, description, category);
        this.source = source != null ? source : "Other";
    }

     // Constructor for loading from file
    public Income(int id, double amount, String description, String category, 
                  LocalDate date) {
        super(id, amount, description, category, date);
        this.source = "Other"; // Default for legacy data
    }
    
    // Constructor with source for file loading (future enhancement)
    public Income(int id, double amount, String description, String category, 
                  LocalDate date, String source) {
        super(id, amount, description, category, date);
        this.source = source != null ? source : "Other";
    }
    
    @Override
    public String getType() {
        return "INCOME";
    }
    
    @Override
    public double getBalanceImpact() {
        return amount; // WHY positive? Income increases balance
    }
    
    @Override
    public String getDisplaySymbol() {
        return "+"; // WHY +? Visual indicator of money coming in
    }
    
    @Override
    protected String getAdditionalFields() {
        // Format: just the source for now
        return source;
    }
    
    // Income-specific methods
    public String getSource() { return source; }
    
    public void setSource(String source) {
        this.source = source != null && !source.trim().isEmpty() 
                     ? source.trim() : "Other";
    }
    
    // WHY override? Income might have additional display information
    @Override
    public String toString() {
        String baseString = super.toString();
        return baseString + " | Source: " + source;
    }
}
