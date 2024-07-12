package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository implements Crudable<Account> {

    @Override
    public boolean update(Account updatedAccount) {
        //TODO: Implement update method
        return false;
    }

    @Override
    public boolean delete(int userId) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "delete from accounts where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            if(ps.executeUpdate() == 0)
                throw new RuntimeException("The account could not be removed from the database.....");

            System.out.println("Account successfully removed from the database.....");
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account create(Account newAccount) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "insert into accounts(user_id, account_type, account_balance) values(?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, newAccount.getUserId());
            //ps.setString(2, (newAccount.getAccountType())); //TODO: set to type enum
            ps.setDouble(3, newAccount.getAccountBalance());

            if(ps.executeUpdate() == 0)
                throw new RuntimeException("Account could not be inserted into the database");

            return newAccount;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account findById(int userId) {

        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "select * from accounts where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            return generateAccountFromResultSet(rs);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Account generateAccountFromResultSet(ResultSet rs) throws SQLException{
        Account account = new Account();

        account.setUserId(rs.getInt("user_id"));
        //account.setAccountType(rs.getString("account_type")); //TODO: Figure out how to get enum
        account.setAccountBalance(rs.getDouble("account_balance"));

        return account;
    }

    public boolean updateAccountBalance(int userId, double newBalance) {
        try (Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "update accounts set account_balance = ? where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);

            if (ps.executeUpdate() == 0)
                throw new RuntimeException("Account could not be inserted into the database");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
