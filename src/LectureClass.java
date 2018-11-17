import java.sql.Time;
import java.util.Date;

public class LectureClass {
	private String classID;
	private String title;
	private String startTime;
	private String endTime;
	private String startDate;
	private String endDate;
	private String recurring;
	
	//constructor
	public LectureClass( String classID, String title, String startTime, String endTime,String startDate, String endDate, String recurring) {
		this.classID = classID;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.recurring = recurring;
	}
	
	//setters
	public void setClassID(String classID) {
		this.classID = classID;
	}
	
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
	
	
	//getters
	public String getClassID() {
		return classID;
	}
	
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
	
}
