package com.virag.banking.exception;

/**
 * Thrown when a withdrawal is attempted for more than the account's
 * current balance. Kept as a checked exception so callers are forced
 * to consciously handle the "not enough money" business case, rather
 * than it getting lost among generic SQLExceptions.
 */
public class InsufficientBalanceException extends Exception {

    private final double currentBalance;
    private final double requestedAmount;

    public InsufficientBalanceException(double currentBalance, double requestedAmount) {
        super("Insufficient balance. Current balance: " + currentBalance +
                ", requested withdrawal: " + requestedAmount);
        this.currentBalance = currentBalance;
        this.requestedAmount = requestedAmount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }
}
