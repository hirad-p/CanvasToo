package storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;


public class Storage {
	protected Properties props;
	
	protected Connection getConnection() {
		loadProps();
		String url = props.getProperty("MYSQL_DB_URL");
		String user = props.getProperty("MYSQL_DB_USERNAME");
		String pass = props.getProperty("MYSQL_DB_PASSWORD");
		Connection connection = null;
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return connection;
	}	

	protected void loadProps() {
		props = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("db.properties");
			try {
				props.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}
}
