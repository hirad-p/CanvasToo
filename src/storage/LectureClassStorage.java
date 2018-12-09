package storage;

import gui.diplayer.util.LectureClassChange;
import model.LectureClass;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class LectureClassStorage extends Storage {
    /**
     * FUNCTIONAL REQUIREMENT 4
     * A user must be able to add a class
     *
     * @param lectureClass
     * @param user
     * @throws SQLException
     */
    public void addClass(LectureClass lectureClass, User user) throws SQLException {
        String sql =
                "INSERT INTO classes (id, title, startTime, endTime, startDate, endDate, recurring, uID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, lectureClass.getClassID());
        statement.setString(2, lectureClass.getTitle());
        statement.setTime(3, lectureClass.getStartTime());
        statement.setTime(4, lectureClass.getEndTime());
        statement.setDate(5, lectureClass.getStartDate());
        statement.setDate(6, lectureClass.getEndDate());
        statement.setString(7, lectureClass.getRecurring());
        statement.setString(8, user.getId());

        statement.executeUpdate();
        connection.close();
    }

    /**
     * FUNCTIONAL REQUIREMENT 5
     *
     * @param lectureClass
     * @param user
     * @param key
     * @param value
     * @throws SQLException
     */
    public void editClass(LectureClass lectureClass, User user, String key, String value) throws SQLException {
        String sql = "UPDATE classes SET " + key + "=? WHERE id=? AND uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, value);
        stmnt.setString(2, lectureClass.getClassID());
        stmnt.setString(3, user.getId());
        stmnt.executeUpdate();
    }

    public void editClass(LectureClassChange change) throws SQLException {
        String sql = "UPDATE classes SET " + change.key + "=? WHERE id=? AND uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, change.value);
        stmnt.setString(2, change.classId);
        stmnt.setString(3, change.uId);
        stmnt.executeUpdate();
    }

    /**
     * @param classId
     * @param userId
     * @throws SQLException
     */
    public void deleteClass(String classId, String userId) throws SQLException {
        String sql = "DELETE FROM classes WHERE id=? AND uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, classId);
        stmnt.setString(2, userId);
        stmnt.executeUpdate();
        conn.close();
    }

    /**
     * Function requirement 18
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<User> getFullTimeStudents() throws SQLException {
        String sql = "SELECT USERS.firstname, USERS.lastname " +
                "FROM USERS " +
                "WHERE 4 <= (SELECT COUNT(*) " +
                "FROM classes " +
                "WHERE classes.uID = USERS.id)";

        ArrayList<User> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        ResultSet set = stmnt.executeQuery();
        while (set.next()) {
            list.add(new User(set.getString(1), set.getString(2)));
        }

        return list;
    }

    /**
     * FUNCTION REQUIREMENT 20
     *
     * @return
     */
    public ArrayList<User> getUsersFromClasses(String class1, String class2) throws SQLException {
        String sql = "SELECT table1.firstname, table1.lastname "
                + "FROM (SELECT USERS.id, USERS.firstname, USERS.lastname, classes.title "
                + "FROM USERS,classes "
                + "WHERE USERS.id = classes.uID AND classes.id =? ) AS table1 "
                + "WHERE table1.id IN "
                + "(SELECT table2.id "
                + "FROM (SELECT USERS.id, USERS.firstname, USERS.lastname, classes.title "
                + "FROM USERS,classes "
                + "WHERE USERS.id = classes.uID AND classes.id =? ) AS table2)";

        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the parameters
        statement.setString(1, class1);
        statement.setString(2, class2);
        ResultSet set = statement.executeQuery();
        ArrayList<User> list = new ArrayList<>();
        while (set.next()) {
            list.add(new User(
                    set.getString("firstName"),
                    set.getString("lastName")
            ));
        }
        return list;
    }


    public ArrayList<LectureClass> getClasses(User user) throws SQLException {
        String sql;
        PreparedStatement statement;
        Connection conn = getConnection();
        if (user == null) {
            sql = "SELECT DISTINCT id, title, startTime, endTime, startDate, endDate, recurring FROM classes";
            statement = conn.prepareStatement(sql);
        } else {
            sql = "SELECT DISTINCT id, title, startTime, endTime, startDate, endDate, recurring FROM classes WHERE uId=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, user.getId());
        }

        ResultSet set = statement.executeQuery();
        ArrayList<LectureClass> list = new ArrayList<>();
        while (set.next()) {
            list.add(new LectureClass(
                    set.getString("id"),
                    set.getString("title"),
                    set.getString("startTime"),
                    set.getString("endTime"),
                    set.getString("startDate"),
                    set.getString("endDate"),
                    set.getString("recurring")
            ));
        }
        return list;
    }
}
