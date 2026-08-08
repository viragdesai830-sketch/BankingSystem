package com.virag.banking.service;

import com.virag.banking.dao.AccountDAO;
import com.virag.banking.exception.InsufficientBalanceException;
import com.virag.banking.model.Account;

import java.sql.SQLException;
import java.util.List;

/**
 * Business logic layer. Enforces the rules App.java shouldn't have to
 * know about (no negative deposits, no overdrawing, no editing an
 * immutable field) and delegates persistence to AccountDAO.
 */
public class AccountService {

    private final AccountDAO accountDAO = new AccountDAO();

    public void createAccount(int accountNo, String holderName, double openingBalance,
                               String phone, String email) throws SQLException {

        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }

        if (accountDAO.accountExists(accountNo)) {
            throw new IllegalStateException("Account number already exists.");
        }

        Account account = new Account(accountNo, holderName, openingBalance, phone, email);
        boolean created = accountDAO.insertAccount(account);

        if (!created) {
            throw new IllegalStateException("Failed to create account.");
        }
    }

    public List<Account> viewAllAccounts() throws SQLException {
        return accountDAO.getAllAccounts();
    }

    public Account searchAccount(int accountNo) throws SQLException {
        Account account = accountDAO.getAccount(accountNo);
        if (account == null) {
            throw new IllegalStateException("Account not found.");
        }
        return account;
    }

    public double deposit(int accountNo, double amount) throws SQLException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0.");
        }

        Account account = accountDAO.getAccount(accountNo);
        if (account == null) {
            throw new IllegalStateException("Account not found.");
        }

        double newBalance = account.getBalance() + amount;
        boolean updated = accountDAO.updateBalance(accountNo, newBalance);

        if (!updated) {
            throw new IllegalStateException("Failed to deposit money.");
        }

        return newBalance;
    }

    public double withdraw(int accountNo, double amount)
            throws SQLException, InsufficientBalanceException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0.");
        }

        Account account = accountDAO.getAccount(accountNo);
        if (account == null) {
            throw new IllegalStateException("Account not found.");
        }

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(account.getBalance(), amount);
        }

        double newBalance = account.getBalance() - amount;
        boolean updated = accountDAO.updateBalance(accountNo, newBalance);

        if (!updated) {
            throw new IllegalStateException("Failed to withdraw money.");
        }

        return newBalance;
    }

    public void updateAttribute(String attribute, int accountNo, String newValue) throws SQLException {
        boolean updated = accountDAO.updateAttribute(attribute, accountNo, newValue);
        if (!updated) {
            throw new IllegalStateException("Account not found.");
        }
    }

    public void deleteAccount(int accountNo) throws SQLException {
        boolean deleted = accountDAO.deleteAccount(accountNo);
        if (!deleted) {
            throw new IllegalStateException("Account not found.");
        }
    }
}
