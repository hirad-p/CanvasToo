package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserStorage extends Storage {
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
		statement.executeUpdate();
		conn.close();
	}

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
