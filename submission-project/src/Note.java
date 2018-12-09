import java.sql.Timestamp;

public class Note {
    private String id;
    private String title;
    private String note;
    private Timestamp lastModified;


    //constructor
    public Note(String title, String note) {
        this.title = title;
        this.note = note;
        lastModified = new Timestamp(System.currentTimeMillis());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Note(String id, String title, String note, Timestamp lastModified) {
        this(title, note);
        this.id = id;
        this.lastModified = lastModified;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}