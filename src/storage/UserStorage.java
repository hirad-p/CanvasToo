package storage;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStorage extends Storage {

    /**
     *  FUNCTION REQUIREMENT 1
     * @param user
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        Connection conn = this.getConnection();
        String sql = "insert into users(firstName, lastName, email, pass)" + "values (?, ?, ?, ?)";

        // prepare statement
        PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        // set the parameters
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());

        // execute the query
        int affected = statement.executeUpdate();
        if (affected != 0) {
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                user.setId(String.valueOf(set.getInt(1)));
                System.out.println("User created with id of " + user.getId());
            }
        }
    }

    /**
     * FUNTION REQUIREMENT 2
     * @param user
     * @throws SQLException
     */
    public void updateUser(User user) throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement statement;
        String sql = "update users set email=?, pass=? where ";
        if (user.getId() != null) {
            sql += " id=?";
            statement = conn.prepareStatement(sql);
            statement.setString(3, user.getId());
        } else {
            statement = conn.prepareStatement(sql);
            sql += " firstName=? and lastName=?";
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
        }
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());

        //Execute the query
        statement.executeUpdate();
        conn.close();
    }

    /**
     * FUNTION REQUIREMENT 3
     * @param user
     * @throws SQLException
     */
    public void deleteUser(User user) throws SQLException {
        Connection conn = this.getConnection();
        String sql = "delete from users where id=?";

        // prepare statement
        PreparedStatement statement = conn.prepareStatement(sql);

        // set the parameters
        statement.setString(1, user.getId());

        //Execute the query
        statement.executeUpdate();
        conn.close();
    }

    public User getUser(String email, String password) throws SQLException {
        Connection conn = this.getConnection();
        String sql = "select * from users where email=? and pass=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);

        ResultSet set = statement.executeQuery();
        while(set.next()) {
            return new User(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5));
        }

        return null;
    }
}
