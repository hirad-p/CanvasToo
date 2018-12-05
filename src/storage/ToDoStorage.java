package storage;

import gui.diplayer.util.ToDoChange;
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

    public void addToDo(ToDo todo, User user) throws SQLException {
        Connection conn = this.getConnection();
        String sql = "insert into todos(title, due, reminder, classID, uID)" + "values (?, ?, ?, ?, ?)";

        // prepare statement
        PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        // set the parameters
        statement.setString(1, todo.getTitle());
        statement.setString(2, todo.getDue());
        statement.setString(3, todo.getReminder());
        statement.setString(4, todo.getClassId());
        statement.setString(5, user.getId());

        // execute the query
        int affected = statement.executeUpdate();
        if (affected != 0) {
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                todo.setId(String.valueOf(set.getInt(1)));
                System.out.println("Todo created with id of " + todo.getId());
            }
        }
    }

    public void editToDo(ToDoChange change) throws SQLException {
        Connection conn = getConnection();
        String sql = "update todos set ?=? where id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, change.key);
        statement.setString(2, change.value);
        statement.setString(3, change.id);
        statement.executeUpdate();
    }

    public void markDone(ToDo todo) throws SQLException {
        Connection conn = getConnection();
        String sql = "delete from todos where id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, todo.getId());
        statement.executeUpdate();
    }

    public ArrayList<ToDo> getTodos(User user, LectureClass lectureClass) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement statement;
        String sql = "select * from todos where uID=?";
        if (lectureClass != null) {
            sql += " and classId=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, user.getId());
            statement.setString(2, lectureClass.getClassID());
        } else {
            statement = conn.prepareStatement(sql);
            statement.setString(1, user.getId());
        }

        ResultSet set = statement.executeQuery();
        ArrayList<ToDo> list = new ArrayList<>();
        while (set.next()) {
            list.add(new ToDo(
                    set.getString("id"),
                    set.getString("title"),
                    set.getString("due"),
                    set.getString("reminder"),
                    set.getString("classID")
            ));
        }
        return list;
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
