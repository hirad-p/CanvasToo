package storage;

import java.sql.SQLException;

import model.User;

public class StorageTester {

	public static void main(String[] args) throws SQLException {
		UserStorage store = new UserStorage();
		
		// add a test user
		User user = new User("Hirad", "P", "test@test.com", "test");
		store.addUser(user);
		
		// update a test user
		user.setId("2");
		user.setEmail("1@1.com");
		user.setPassword("test2");
		store.updateUser(user);
		
		// delete a user
		store.deleteUser(user);
	}

}
