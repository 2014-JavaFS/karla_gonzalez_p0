package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.interfaces.Serviceable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository implements Serviceable<Account> {
    /**
     * Executes a SQL query to insert a new account into the database
     * Will catch and log an SQLException if the query cannot be executed
     *
     * @param newAccount The account information to be inserted into the database
     *
     * @return  The account inserted into the database
     *          Null if an SQLException occurs
     */
    @Override
    public Account create(Account newAccount) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "insert into accounts(user_id, account_type, account_balance) values(?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, newAccount.getUserId());
            ps.setString(2, String.valueOf(newAccount.getAccountType()));
            ps.setDouble(3, newAccount.getAccountBalance());

            if(ps.executeUpdate() == 0)
                throw new RuntimeException("Account could not be inserted into the database");

            return newAccount;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes a SQL query to search for and return the account matching the provided user ID
     * Will catch and log an SQLException if the query cannot be executed
     *
     * @param userId The ID to look for
     *
     * @return  The account generated from the ResultSet
     *          Null if an SQLException occurs
     */
    @Override
    public Account findById(int userId) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "select * from accounts where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                throw new DataNotFoundException("No account found under that id; please create one to continue.");

            return generateAccountFromResultSet(rs);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Initializes an instance of the Account class with the information retrieved from the database
     * Will catch and log an SQLException if it is unable to generate an account
     *
     * @param rs The ResultSet containing the data retrieved from the database
     *
     * @return  The account generated from the ResultSet
     *          Null if an SQLException occurs
     */
    private Account generateAccountFromResultSet(ResultSet rs) {
        Account account = new Account();

        try {
            account.setUserId(rs.getInt("user_id"));
            account.setAccountType(Account.AccountType.valueOf(rs.getString("account_type")));
            account.setAccountBalance(rs.getDouble("account_balance"));

            return account;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes an SQL query to update the account balance in the database
     *
     * @param userId        The id of the account owner
     * @param newBalance    The new account balance
     *
     * @return  True if the query could be executed successfully
     *          False if an SQLException occurs
     */
    public boolean updateAccountBalance(int userId, double newBalance) {
        try (Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "update accounts set account_balance = ? where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);

            if (ps.executeUpdate() == 0)
                throw new RuntimeException("Account balance could not be updated");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
