package com.virag.banking;

import com.virag.banking.exception.InsufficientBalanceException;
import com.virag.banking.model.Account;
import com.virag.banking.service.AccountService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Presentation layer only. Knows how to talk to the user via the
 * console; knows nothing about SQL, JDBC, or business rules — all of
 * that lives in AccountService / AccountDAO. Keeping App this thin
 * means the same AccountService could later be reused behind a REST
 * controller or a GUI without rewriting the business logic.
 */
public class App {

    private static final AccountService accountService = new AccountService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to our Bank Management system!...");

        while (running) {
            printMenu();

            System.out.print("What do you want to do? Enter that number : ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        handleCreateAccount(sc);
                        break;
                    case 2:
                        handleViewAccounts();
                        break;
                    case 3:
                        handleSearchAccount(sc);
                        break;
                    case 4:
                        handleDeposit(sc);
                        break;
                    case 5:
                        handleWithdraw(sc);
                        break;
                    case 6:
                        handleUpdateAttribute(sc);
                        break;
                    case 7:
                        handleDeleteAccount(sc);
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid Choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type entered. Please try again.");
                sc.nextLine();
            } catch (IllegalArgumentException | IllegalStateException e) {
                // business rule violations bubbled up from AccountService
                System.out.println(e.getMessage());
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }

        sc.close();

        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Throwable ignored) {
            // ok if this driver version doesn't have it
        }
    }

    private static void printMenu() {
        System.out.println(" ");
        System.out.println("These are the features we serve.");
        System.out.println("1) Create Account");
        System.out.println("2) View all Accounts");
        System.out.println("3) Search Account");
        System.out.println("4) Deposit Money");
        System.out.println("5) Withdraw Money");
        System.out.println("6) Update Account Details");
        System.out.println("7) Delete Account");
        System.out.println("8) Exit");
        System.out.println(" ");
    }

    private static void handleCreateAccount(Scanner sc) throws SQLException {
        System.out.println("You selected Create Account");

        System.out.print("Enter new Account number --> ");
        int accNum = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name --> ");
        String name = sc.nextLine();

        System.out.print("Enter Amount (deposit while creating account) --> ");
        double amount = sc.nextDouble();

        System.out.print("Enter Phone number --> ");
        String phone = sc.next();

        System.out.print("Enter Email Address --> ");
        String email = sc.next();

        accountService.createAccount(accNum, name, amount, phone, email);
        System.out.println("Account created successfully.");
    }

    private static void handleViewAccounts() throws SQLException {
        System.out.println("You selected View all Accounts");

        List<Account> accounts = accountService.viewAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static void handleSearchAccount(Scanner sc) throws SQLException {
        System.out.println("You selected Search Account");
        System.out.print("Enter Account number you want to search : ");
        int accNum = sc.nextInt();

        Account account = accountService.searchAccount(accNum);
        System.out.println(account);
    }

    private static void handleDeposit(Scanner sc) throws SQLException {
        System.out.println("You selected Deposit Money");
        System.out.print("Enter how much money you want to deposit : ");
        double amount = sc.nextDouble();

        System.out.print("Enter account number to deposit into : ");
        int accNum = sc.nextInt();

        double newBalance = accountService.deposit(accNum, amount);
        System.out.println("Money deposited successfully.");
        System.out.println("Current Balance : " + newBalance);
    }

    private static void handleWithdraw(Scanner sc) throws SQLException, InsufficientBalanceException {
        System.out.println("You selected Withdraw Money");
        System.out.print("Enter how much money you want to withdraw : ");
        double amount = sc.nextDouble();

        System.out.print("Enter account number to withdraw from : ");
        int accNum = sc.nextInt();

        double newBalance = accountService.withdraw(accNum, amount);
        System.out.println("Money withdrawn successfully.");
        System.out.println("Remaining Balance : " + newBalance);
    }

    private static void handleUpdateAttribute(Scanner sc) throws SQLException {
        System.out.println("You selected Update Account Details");

        System.out.println("What do you want to update (name, phone, email) : ");
        String attribute = sc.next();

        System.out.print("Enter account number to update : ");
        int accNum = sc.nextInt();

        System.out.print("Enter new value : ");
        String newValue = sc.next();

        accountService.updateAttribute(attribute, accNum, newValue);
        System.out.println("Account updated successfully.");
    }

    private static void handleDeleteAccount(Scanner sc) throws SQLException {
        System.out.println("You selected Delete Account");
        System.out.print("Enter Account number you want to Delete : ");
        int accNum = sc.nextInt();

        accountService.deleteAccount(accNum);
        System.out.println("Account Deleted successfully.");
    }
}
