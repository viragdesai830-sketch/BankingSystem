package com.virag.banking.dao;

import com.virag.banking.config.DBConnection;
import com.virag.banking.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Accounts table.
 *
 * Responsibility: talk to the database and translate rows <-> Account
 * objects. Deliberately contains NO business rules (e.g. "can't withdraw
 * more than balance") — that belongs in AccountService. This keeps the
 * DAO reusable and easy to test/mock independently of business logic.
 */
public class AccountDAO {

    public boolean accountExists(int accountNo) throws SQLException {
        String query = "SELECT 1 FROM Accounts WHERE account_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, accountNo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean insertAccount(Account account) throws SQLException {
        String query = "INSERT INTO Accounts(account_no, holder_name, balance, phone, email) VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, account.getAccountNo());
            ps.setString(2, account.getHolderName());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getPhone());
            ps.setString(5, account.getEmail());

            return ps.executeUpdate() > 0;
        }
    }

    public Account getAccount(int accountNo) throws SQLException {
        String query = "SELECT * FROM Accounts WHERE account_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, accountNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null; // not found — caller decides how to react
            }
        }
    }

    public List<Account> getAllAccounts() throws SQLException {
        String query = "SELECT * FROM Accounts";
        List<Account> accounts = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        }

        return accounts;
    }

    /**
     * Sets the balance to an exact value. Deposit/withdraw arithmetic
     * (adding or subtracting) is a business decision made by the
     * service layer, not the DAO — the DAO just persists whatever
     * final number it's given.
     */
    public boolean updateBalance(int accountNo, double newBalance) throws SQLException {
        String query = "UPDATE Accounts SET balance = ? WHERE account_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDouble(1, newBalance);
            ps.setInt(2, accountNo);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateAttribute(String attribute, int accountNo, String newValue) throws SQLException {
        String column;

        switch (attribute) {
            case "name":
                column = "holder_name";
                break;
            case "phone":
                column = "phone";
                break;
            case "email":
                column = "email";
                break;
            default:
                throw new IllegalArgumentException("Cannot update immutable or unknown attribute: " + attribute);
        }

        // column is chosen from a fixed whitelist above (never built from
        // raw user input), so this string-built query is still safe from
        // SQL injection; the *value* remains parameterized.
        String query = "UPDATE Accounts SET " + column + " = ? WHERE account_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, newValue);
            ps.setInt(2, accountNo);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteAccount(int accountNo) throws SQLException {
        String query = "DELETE FROM Accounts WHERE account_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, accountNo);

            return ps.executeUpdate() > 0;
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("account_no"),
                rs.getString("holder_name"),
                rs.getDouble("balance"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}
