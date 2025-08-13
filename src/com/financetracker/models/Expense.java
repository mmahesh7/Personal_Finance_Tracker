package com.financetracker.models;

import java.time.LocalDate;

public class Expense extends Transaction {
    private boolean isEssential; // NEW: Track if expense is essential
    private String paymentMethod; // NEW: How was it paid (cash, card, etc.)
    
    // Convenience constructor used when UI doesn't provide payment method
    public Expense(double amount, String description, String category, boolean isEssential) {
        this(amount, description, category, isEssential, "Unknown");
    }
    // Constructor for new expense
    public Expense(double amount, String description, String category, 
                   boolean isEssential, String paymentMethod) {
        super(amount, description, category);
        this.isEssential = isEssential;
        this.paymentMethod = paymentMethod != null ? paymentMethod : "Unknown";
    }
    
    // Constructor for loading from file (basic)
    public Expense(int id, double amount, String description, String category, 
                   LocalDate date) {
        super(id, amount, description, category, date);
        this.isEssential = true; // Default to essential for legacy data
        this.paymentMethod = "Unknown";
    }
    
    // Constructor for loading with additional data (future enhancement)
    public Expense(int id, double amount, String description, String category, 
                   LocalDate date, boolean isEssential, String paymentMethod) {
        super(id, amount, description, category, date);
        this.isEssential = isEssential;
        this.paymentMethod = paymentMethod != null ? paymentMethod : "Unknown";
    }
    
    @Override
    public String getType() {
        return "EXPENSE";
    }
    
    @Override
    public double getBalanceImpact() {
        return -amount; // WHY negative? Expenses decrease balance
    }
    
    @Override
    public String getDisplaySymbol() {
        return "-"; // WHY -? Visual indicator of money going out
    }
    
    // Expense-specific methods
    public boolean isEssential() { return isEssential; }
    public String getPaymentMethod() { return paymentMethod; }
    
    public void setEssential(boolean essential) {
        this.isEssential = essential;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod != null && !paymentMethod.trim().isEmpty() 
                           ? paymentMethod.trim() : "Unknown";
    }
    
    // Expense-specific business logic
    public boolean canBeReduced() {
        return !isEssential; // WHY? Only non-essential expenses can be cut
    }
    
    @Override
    public String toString() {
        String baseString = super.toString();
        String essentialStatus = isEssential ? "Essential" : "Non-essential";
        return baseString + String.format(" | %s | Paid: %s", essentialStatus, paymentMethod);
    }
}