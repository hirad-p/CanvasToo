package storage;

import gui.diplayer.util.EventChange;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventStorage extends Storage {
    public void addEvent(Event event, User user, LectureClass lectureClass) throws SQLException {
        String sql =
                "insert into events (title, startTime, endTime, startDate, endDate, recurring, uID, classID) "
                        + "values (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1, lectureClass.getTitle());
        statement.setTime(2, lectureClass.getStartTime());
        statement.setTime(3, lectureClass.getEndTime());
        statement.setDate(4, lectureClass.getStartDate());
        statement.setDate(5, lectureClass.getEndDate());
        statement.setString(6, lectureClass.getRecurring());
        statement.setString(7, user.getId());
        statement.setString(8, lectureClass.getClassID());

        // execute the query
        int affected = statement.executeUpdate();
        if (affected != 0) {
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                event.setId(String.valueOf(set.getInt(1)));
                System.out.println("Todo created with id of " + event.getId());
            }
        }
    }

    public void editEvent(Event event, LectureClass lectureClass) throws SQLException {
        String sql = "update events set title=?, startTime=?, endTime=?, startDate=?, endDate=?, recurring=?, classID=? where id=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, event.getTitle());
        stmnt.setString(2, event.getStartTime());
        stmnt.setString(3, event.getEndTime());
        stmnt.setString(4, event.getStartDate());
        stmnt.setString(5, event.getEndDate());
        stmnt.setString(6, event.getRecurring());
        stmnt.setString(7, lectureClass.getClassID());
        stmnt.setString(8, event.getId());
        stmnt.executeUpdate();
    }

    public void editEvent(EventChange change) throws SQLException {
        String sql = "update events set " + change.key + "=? where id=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, change.value);
        stmnt.setString(2, change.id);
        stmnt.executeUpdate();
    }

    public void deleteEvent(Event event) throws SQLException {
        System.out.println("id" + event.getId());
        String sql = "delete from events where id=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, event.getId());
        stmnt.executeUpdate();
    }

    public ArrayList<Event> getEventsForClass(User user, LectureClass lectureClass) throws SQLException {
        String sql = "select id, title, startTime, endTime, startDate, endDate, recurring from events where classId=? and uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, lectureClass.getClassID());
        stmnt.setString(2, user.getId());

        ArrayList<Event> list = new ArrayList<>();
        ResultSet set = stmnt.executeQuery();
        while(set.next()) {
            list.add(new Event(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    set.getString(6),
                    set.getString(7),
                    lectureClass.getClassID()
            ));
        }

        return list;
    }

    public ArrayList<Count> getEventsPerClass(User user) throws SQLException {
        String sql =  "select classes.id, count(events.id) " +
                "from classes join events on " +
                "classes.id=events.classID and " +
                "classes.uID = ? and " +
                "events.uID = ? " +
                "group by classes.id";

        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, user.getId());
        stmnt.setString(2, user.getId());

        ArrayList<Count> list = new ArrayList<>();
        ResultSet set = stmnt.executeQuery();
        while(set.next()) {
            list.add(new Count(set.getString(1), set.getInt(2)));
        }

        return list;
    }
}
