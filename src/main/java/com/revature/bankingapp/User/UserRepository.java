package com.revature.bankingapp.User;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.revature.bankingapp.BankAppFrontController.logger;

public class UserRepository implements Crudable<User> {

    @Override
    public boolean delete(int userId) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "delete from users where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            if(ps.executeUpdate() == 0)
                throw new RuntimeException("User could not be removed from the database.....");

            System.out.println("User and their account(s) successfully removed from the database.....");
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User create(User newUser) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "insert into users(first_name, last_name, email, password) values(?, ?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newUser.getFirstName());
            ps.setString(2, newUser.getLastName());
            ps.setString(3, newUser.getEmail());
            ps.setString(4, newUser.getPassword());

            if(ps.executeUpdate() == 0)
                throw new RuntimeException("User could not be inserted into the database");

            return newUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes a SQL query to search for and return the user matching the provided user ID
     * Will catch and log an SQLException if the query cannot be executed
     *
     * @param userId The ID to look for
     *
     * @return  The user generated from the ResultSet
     *          Null if an SQLException occurs
     */
    @Override
    public User findById(int userId) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            logger.info("User id provided by the service is {}", userId);
            String sql = "select * from users where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                throw new DataNotFoundException("No user found under that Id.....");

            return generateUserFromResultSet(rs);

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes a SQL query to find and return the user matching the email and password provided
     * Will catch and log an SQLException if the query cannot be executed
     *
     * @param email     The email address to search for in the database
     * @param password  The password to search for in the database
     *
     * @return  The user generated from the ResultSet
     *          Null if an SQLException occurs
     */
    public User findByEmailAndPassword(String email, String password) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "select * from users where email = ? and password = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                throw new DataNotFoundException("No account under those credentials.....");

            return generateUserFromResultSet(rs);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Initializes an instance of the User class with the information retrieved from the database
     * Will catch and log an SQLException if it is unable to generate a user
     *
     * @param rs The ResultSet containing the data retrieved from the database
     *
     * @return  The user generated from the ResultSet
     *          Null if an SQLException occurs
     */
    private User generateUserFromResultSet(ResultSet rs) {
        User user = new User();

        try {
            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
