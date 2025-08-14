package com.financetracker.exceptions;

public class InsufficientFundsException extends Exception {
    private double requestedAmount;
    private double availableBalance;

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, double requestedAmount, double availableBalance) {
        super(message);
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }

    public double getRequestedAmount() { return requestedAmount; }
    public double getAvailableBalance() { return availableBalance; }
}
