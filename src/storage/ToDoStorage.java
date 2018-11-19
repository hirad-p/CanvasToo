package storage;

import model.LectureClass;
import model.ToDo;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

public class ToDoStorage extends Storage {

    public ToDo addToDo(ToDo todo, User user, LectureClass lectureClass) {
        return null;
    }

    /**
     * FUNCTION REQUIREMENT 19
     * Getting all users with no todos
     * @return
     */
    public ArrayList<User> getUsersWithNoToDos() throws SQLException {
        String sql = "select users.firstName, users.lastName, users.email "
            + "from users join todos on (users.id = todos.uID) "
            + "group by users.id "
            + "having count(todos.id) <= 0";
        System.out.println(sql);

        Connection connection = getConnection();
        PreparedStatement stmnt = connection.prepareStatement(sql);
        ResultSet set = stmnt.executeQuery();
        ArrayList<User> list = new ArrayList<>();
        while(set.next()) {
            list.add(new User(
                set.getString("firstName"),
                set.getString("lastName"),
                set.getString("email")
            ));
        }

        connection.close();
        return list;
    }

    public ArrayList<User> getUsersWithOverDueTodos() throws SQLException {
        String sql = "select distinct users.firstName, users.lastName " +
            "from users " +
            "where id in (select uID " +
            "from todos " +
            "where ? > due)";

        Date date= new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        Connection connection = getConnection();
        PreparedStatement stmnt = connection.prepareStatement(sql);
        stmnt.setTimestamp(1, ts);
        ResultSet set = stmnt.executeQuery();
        ArrayList<User> list = new ArrayList<>();
        while(set.next()) {
            list.add(new User(
                set.getString(1),
                set.getString(2)
            ));
        }

        connection.close();
        return list;
    }
}
