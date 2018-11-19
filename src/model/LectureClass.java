package model;

import java.sql.Date;
import java.sql.Time;

public class LectureClass {
    private String classID;
    private String title;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    private String recurring;

    //constructor
    public LectureClass(String classID) {
        this.classID = classID;
    }

    public LectureClass(String classID, String title, String startTime, String endTime, String startDate, String endDate, String recurring) {
        this.classID = classID;
        this.title = title;
        this.startTime = Time.valueOf(startTime);
        this.endTime = Time.valueOf(endTime);
        this.startDate = Date.valueOf(startDate);
        this.endDate = Date.valueOf(endDate);
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = Time.valueOf(startTime);;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = Time.valueOf(endTime);;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate =  Date.valueOf(startDate);;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate =  Date.valueOf(endDate);;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }
}
