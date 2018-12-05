package storage;

import model.LectureClass;
import model.Note;
import model.NoteCount;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotesStorage extends Storage {

    public void addNote(Note note, User user, LectureClass lectureClass) throws SQLException{
        Connection conn = this.getConnection();
        String sql = "insert into notes(title, body, uID, classID)" + "values (?, ?, ?, ?)";

        // prepare statement
        PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        // set the parameters
        statement.setString(1, note.getTitle());
        statement.setString(2, note.getNote());
        statement.setString(3, user.getId());
        statement.setString(4, lectureClass.getClassID());

        // execute the query
        int affected = statement.executeUpdate();
        if (affected != 0) {
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                note.setId(String.valueOf(set.getInt(1)));
                System.out.println("Note created with id of " + note.getId());
            }
        }
    }

    public void editNote(Note note, LectureClass lectureClass) throws SQLException {
        String sql = "update notes set title=?, note=?, classId=? where id=?";
        Connection conn = this.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, note.getTitle());
        statement.setString(2, note.getNote());
        statement.setString(3, lectureClass.getClassID());
        statement.setString(4, note.getId());

        statement.executeUpdate();
    }


    public void deleteNote(Note note) throws SQLException {
        String sql = "delete from notes where id=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, note.getId());
        stmnt.executeUpdate();
    }

    public ArrayList<Note> getNotesForClass(User user, LectureClass lectureClass) throws SQLException {
        String sql = "select id, title, note, modified from notes where classId=? and uId=?";
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, lectureClass.getClassID());
        stmnt.setString(2, user.getId());

        ArrayList<Note> list = new ArrayList<>();
        ResultSet set = stmnt.executeQuery();
        while(set.next()) {
            list.add(new Note(set.getString(1), set.getString(2), set.getString(3), set.getTimestamp(4)));
        }

        return list;
    }

    /**
     * Function requirement 16
     * @param user
     * @return
     */
    public ArrayList<NoteCount> getNotesPerClass(User user) throws SQLException {
        String sql =  "select classes.id, count(notes.id) " +
            "from classes join notes on " +
            "classes.id=notes.classID and " +
            "classes.uID = ? and " +
            "notes.uID = ? " +
            "group by classes.id";

        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, user.getId());
        stmnt.setString(2, user.getId());

        ArrayList<NoteCount> list = new ArrayList<>();
        ResultSet set = stmnt.executeQuery();
        while(set.next()) {
            list.add(new NoteCount(set.getString(1), set.getInt(2)));
        }

        return list;
    }
}
