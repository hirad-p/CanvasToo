package storage;

import model.Notes;
import model.NoteCount;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotesStorage extends Storage {

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
