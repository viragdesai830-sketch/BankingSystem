package com.virag.banking.model;

/**
 * Plain data holder representing one row in the Accounts table.
 * Has no database or business logic of its own — that belongs
 * in AccountDAO and AccountService respectively.
 */
public class Account {

    private int accountNo;
    private String holderName;
    private double balance;
    private String phone;
    private String email;

    public Account() {
    }

    public Account(int accountNo, String holderName, double balance, String phone, String email) {
        this.accountNo = accountNo;
        this.holderName = holderName;
        this.balance = balance;
        this.phone = phone;
        this.email = email;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNo +
                "\nName: " + holderName +
                "\nBalance: " + balance +
                "\nPhone: " + phone +
                "\nEmail: " + email +
                "\n----------------";
    }
}
