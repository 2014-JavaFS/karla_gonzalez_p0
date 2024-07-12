package com.revature.bankingapp.User;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements Crudable<User> {

    @Override
    public boolean update(User updatedUser) {
        //TODO: Implement update method
        return false;
    }

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
    public User create(User newUser) throws InvalidInputException {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "insert into users(user_id, first_name, last_name, email, password) values(?, ?, ?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, newUser.getUserId());
            ps.setString(2, newUser.getFirstName());
            ps.setString(3, newUser.getLastName());
            ps.setString(4, newUser.getEmail());
            ps.setString(5, newUser.getPassword());

            if(ps.executeUpdate() == 0)
                throw new RuntimeException("User could not be inserted into the database");

            return newUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(int userId) {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "select * from users where user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            return generateUserFromResultSet(rs);

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User generateUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }

    public User findByEmailAndPassword(String email, String password) {

        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "select * from users where email = ? and password = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            User user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));

            return user;

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
