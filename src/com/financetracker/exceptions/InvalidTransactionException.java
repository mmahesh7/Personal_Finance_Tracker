package com.financetracker.exceptions;

public class InvalidTransactionException extends Exception{
    public InvalidTransactionException(String message) {
        super(message);
    }
}
