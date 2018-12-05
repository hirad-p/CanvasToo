package model;

public class ToDo {
    private String title;
    private String due;
    private String reminder;
    private String classId;


    //constructor
    public ToDo(String title, String due, String reminder, String classId) {
        this(title, due, reminder);
        this.classId = classId;
    }

    public ToDo(String title, String due, String reminder) {
        this.title = title;
        this.due = due;
        this.reminder = reminder;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }


    //getters
    public String getTitle() {
        return title;
    }

    public String getDue() {
        return due;
    }

    public String getReminder() {
        return reminder;
    }

    public String getClassId() {
        return classId;
    }

}