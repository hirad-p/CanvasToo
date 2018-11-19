package storage;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        PreparedStatement statement = conn.prepareStatement(sql);

        // set the parameters
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());

        // execute the query
        // TODO fetch the id and update the user object
        statement.executeUpdate();
        conn.close();
    }

    /**
     * FUNTION REQUIREMENT 2
     * @param user
     * @throws SQLException
     */
    public void updateUser(User user) throws SQLException {
        Connection conn = this.getConnection();
        String sql = "update users set email=?, pass=?"
            + "where firstName=? and lastName=?";

        // prepare statement
        PreparedStatement statement = conn.prepareStatement(sql);

        // set the parameters
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());

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
}
