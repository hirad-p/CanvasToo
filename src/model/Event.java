package model;

public class Event {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;
    private String recurring;
    private String classID;

    //constructor
    public Event(String id, String title, String startTime, String endTime, String startDate, String endDate, String recurring, String classId) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurring = recurring;
        this.classID = classId;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    //getters
    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRecurring() {
        return recurring;
    }

    public String getId() { return id; }

    public String getClassID() {
        return classID;
    }
}