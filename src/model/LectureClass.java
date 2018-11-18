package model;

public class LectureClass {
    private String classID;
    private String title;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;
    private String recurring;

    //constructor
    public LectureClass(String classID, String title, String startTime, String endTime, String startDate, String endDate, String recurring) {
        this.classID = classID;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurring = recurring;
    }

    //getters
    public String getClassID() {
        return classID;
    }

    //setters
    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }
}
