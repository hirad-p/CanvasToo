package storage;

import model.LectureClass;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class LectureClassStorage extends Storage {

    /**
     * FUNCTIONAL REQUIREMENT 4
     * A user must be able to add a class
     * @param lectureClass
     * @param user
     * @throws SQLException
     */
    public void addClass(LectureClass lectureClass, User user) throws SQLException {
        String sql =
            "insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(sql);

        Connection connection = this.getConnection();
        PreparedStatement statement =  connection.prepareStatement(sql);

        statement.setString(1, lectureClass.getClassID());
        statement.setString(2, lectureClass.getTitle());
        statement.setTime(3, lectureClass.getStartTime());
        statement.setTime(4, lectureClass.getEndTime());
        statement.setDate(5, lectureClass.getStartDate());
        statement.setDate(6, lectureClass.getEndDate());
        statement.setString(7, lectureClass.getRecurring());
        statement.setString(8, user.getId());

        statement.executeUpdate();
    }

    /**
     * FUNCTIONAL REQUIREMENT 5
     * @param lectureClass
     * @param user
     * @param key
     * @param value
     * @throws SQLException
     */
    public void editClass(LectureClass lectureClass, User user, String key, String value) throws SQLException {
        String sql = "update classes set " + key + "=? where id=? and uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, value);
        stmnt.setString(2, lectureClass.getClassID());
        stmnt.setString(3, user.getId());
        stmnt.executeUpdate();
    }

    /**
     *
     * @param classId
     * @param userId
     * @throws SQLException
     */
    public void deleteClass(String classId, String userId) throws SQLException {
        String sql = "delete from classes where id=? and uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, classId);
        stmnt.setString(2, userId);
        stmnt.executeUpdate();
    }

    /**
     * FUNCTION REQUIREMENT 20
     * @param classes
     * @return
     */
    public ArrayList<User> getUsersFromClasses(LectureClass[] classes) throws SQLException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < classes.length; i++) {
            builder.append("select users.firstName, users.lastName from users join classes on (users.id = classes.uID) where classes.id = \"");
            builder.append(classes[i].getClassID());
            if (i < classes.length - 1) {
                builder.append("\" intersect ");
            }
        }
        System.out.println(builder);

        Connection connection = this.getConnection();
        PreparedStatement statement =  connection.prepareStatement(builder.toString());
        ResultSet set = statement.executeQuery();
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
}
