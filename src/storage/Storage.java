package storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class Storage {
    protected Properties props;

    public static Connection getConnection(Properties p) {
        String url = p.getProperty("MYSQL_DB_URL");
        String user = p.getProperty("MYSQL_DB_USERNAME");
        String pass = p.getProperty("MYSQL_DB_PASSWORD");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return connection;
    }

    public static Properties getProps() {
        Properties p = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("db.properties");
            try {
                p.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return p;
    }

    public void archive(String timestamp) throws SQLException {
        Connection conn = getConnection();
        CallableStatement cs = conn.prepareCall("{CALL archiveRows(?)}");
        cs.setTimestamp(1, Timestamp.valueOf(timestamp));
        cs.execute();
    }

    protected Connection getConnection() {
        loadProps();
        return Storage.getConnection(props);
    }

    protected void loadProps() {
        props = Storage.getProps();
    }
}
