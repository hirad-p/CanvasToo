
public class Notes {
	private String title;
	private String note;
	
	
	//constructor
	public Notes(String title, String note) {
		this.title = title;
		this.note = note;
	}
	
	//setters	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDue(String note) {
		this.note = note;
	}
	
	//getters
	public String getTitle() {
		return title;
	}
	
	public String getNote() {
		return note;
	}
}