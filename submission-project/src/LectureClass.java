import java.sql.Date;
import java.sql.Time;
import java.util.Comparator;

public class LectureClass {
    private String classID;
    private String title;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    private String recurring;

    public static Comparator<LectureClass> getComparator() {
        return new Comparator<LectureClass>() {
            public int compare(LectureClass lectureClass1, LectureClass lectureClass2) {
                return lectureClass1.getClassID().compareTo(lectureClass1.getClassID());
            }
        };
    }

    //constructor
    public LectureClass(String classID) {
        this.classID = classID;
    }

    public LectureClass(String classID, String title, String startTime, String endTime) {
        this(classID);
        this.title = title;
        this.startTime = Time.valueOf(startTime);
        this.endTime = Time.valueOf(endTime);
    }

    public LectureClass(String classID, String title, String startTime, String endTime, String startDate, String endDate, String recurring) {
        this(classID, title, startTime, endTime);
        this.startDate = Date.valueOf(startDate);
        this.endDate = Date.valueOf(endDate);
        this.recurring = recurring;
    }

    //getters
    public String getClassID() {
        return classID;
    }

    public String get(String key) {
        if (key.equals("id")) {
            return classID;
        }

        if (key.equals("title")) {
            return title;
        }

        if (key.equals("startTime")) {
            return startTime.toString();
        }

        if (key.equals("endTime")) {
            return endTime.toString();
        }

        if (key.equals("startDate")) {
            return startDate.toString();
        }

        if (key.equals("endDate")) {
            return endDate.toString();
        }

        if (key.equals("recurring")) {
            return recurring;
        }

        return "";
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
