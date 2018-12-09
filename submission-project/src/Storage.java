import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Storage {
    protected Properties props;

    public static Connection getConnection(Properties p) {
        String url = "jdbc:mysql://localhost:3306/canvastoo";
        String user = "root";
        String pass = "jini1234";
        Connection connection = null;
        
        System.out.println(user);
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
