public class ToDo {
	private String id;
    private String title;
    private String due;
    private String reminder;
    private String classId;

    //constructor 
    public ToDo(String title, String due, String reminder) {
        this.title = title;
        this.due = due;
        this.reminder = reminder;
    }

    public ToDo(String title, String due, String reminder, String classId) {
    	this.title = title;
    	this.due = due;
    	this.reminder = reminder;
        this.classId = classId;
    }

    public ToDo(String id, String title, String due, String reminder, String classId) {
        this(title, due, reminder, classId);
        this.id = id;
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

    public void setId(String id) {
        this.id = id;
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

    public String getId() {
        return id;
    }


    // @todo = what is this called ? reflection or something
    public String get(String key) {
        if ("id".equals(key)) {
            return id;
        }

        if ("title".equals(key)) {
            return title;
        }

        if ("due".equals(key)) {
            return due;
        }

        if ("reminder".equals(key)) {
            return reminder;
        }

        if ("classID".equals(key)) {
            return classId;
        }

        return "";
    }
}