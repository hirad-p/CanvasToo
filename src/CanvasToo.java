//Import required packages
import java.sql.*;

public class CanvasToo {
	 //JDBC driver name and database URL
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/CanvasTooDB";

	 //Database credentials
	 static final String USER = "root";
	 static final String PASS = "default$";
	 
	 //Database related variables
	 private static Connection conn = null;
	 private static PreparedStatement prepStmt = null;
	 private static ResultSet rs = null;
	 
	 //Users
	 private static UserAccount user1 = new UserAccount("John", "Doe", "john_doe@email.com", "password1");
	 private static UserAccount user2 = new UserAccount("Jane", "Smith", "jane_smith@email.com", "password2");
	 
	 //Class
	 private static LectureClass class1 = new LectureClass("157A" , "Database Management", "10:30", "11:45", "09/23/2018", "12/18/2018", "Yes");
	 
	 //Event
	 private static Event event1 = new Event();
	 
	 //To-Do
	 private static ToDo todo1 = new ToDo();
	 
	 //Notes
	 private static Notes note1 = new Notes();
	 
	 public static void main(String[] args) {
		 try{
		    //Register JDBC driver (automatically done since JDBC 4.0)
		    Class.forName("com.mysql.jdbc.Driver");
		
		    //Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL, USER, PASS);

/********FUNC REQUIREMENT 1: A user must be able to register using his name, email, and password.**************/	
		    userRegistration();
		    
/********FUNC REQUIREMENT 2: A user must be able to change their email/password if they desire*****************/
		    //if user wants to change his email
		    userChangeEmail();
		    
		    //if user wants to change his password
		    userChangePassword();
		    
/********FUNC REQUIREMENT 3: A user must be able to delete his account******************************************/ 
		    deleteAccount();
		    
/********FUNC REQUIREMENT 4: A user must be able to add a class*************************************************/
		    addClass();
		    
/********FUNC REQUIREMENT 5: A user must be able to edit a class************************************************/
		    editClass();
		    
/********FUNC REQUIREMENT 6: A user must be able to delete a class**********************************************/
		    deleteClass();
		    
/********FUNC REQUIREMENT 7: A user must be able to add an event************************************************/
		    addEvent();
		    
/********FUNC REQUIREMENT 8: A user must be able to edit an event************************************************/
		    editEvent();
		    
/********FUNC REQUIREMENT 9: A user must be able to remove an event**********************************************/
		    deleteEvent();
		    
/********FUNC REQUIREMENT 10: A user must be able to add a to-do*************************************************/
		    addToDo();
		    
/********FUNC REQUIREMENT 11: A user must be able to edit a to-do************************************************/
		    editToDo();
		    
/********FUNC REQUIREMENT 12: A user must be able to delete a to-do***********************************************/
		    deleteToDo();
		    
/********FUNC REQUIREMENT 13: A user must be able to add notes****************************************************/
		    addNotes();
		    
/********FUNC REQUIREMENT 14: A user must be able to edit notes***************************************************/
		    editNotes();
		    
/********FUNC REQUIREMENT 15: A user must be able to delete notes*************************************************/
		    deleteNotes();
		    
		    		    
		    
		    //Process the results
		    while(rs.next()){
		        System.out.println("User ID="+rs.getInt("uid")+", Name="+rs.getString("uname"));
		    }
		    
		 }catch(SQLException se){
		    //Handle errors for JDBC
		    se.printStackTrace();
		 }catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		 }finally{
		    //finally block used to close resources
		    try{
		       if(prepStmt!=null)
		          prepStmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }//end try
		 System.out.println("Goodbye!");
	}//end main
	 
	 
	 static void userRegistration() throws SQLException {	
		String sql = "insert into User (firstName, lastName, email, pass)"
				+ "values (?, ?, ?, ?, ?)";
		
		//prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, user1.getFirstName());
	    prepStmt.setString(2, user1.getLastName());
	    prepStmt.setString(3, user1.getEmail());
	    prepStmt.setString(4, user1.getPassword());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	 
	static void userChangeEmail() throws SQLException {
		String sql = "Update User"
				+ "set email=?"
				+ "where id = 10";
		
		//prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, user1.getEmail());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
		
	}
	
	static void userChangePassword() throws SQLException {
		String sql = "Update User"
				+ "set password=?"
				+ "where id = 10";
		
	    //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, user1.getPassword());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	
	static void deleteAccount() throws SQLException {
		String sql = "delete from User " 
				+ "where firstName=? and lastName=? and email=? and pass=?";
		
		  //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, user1.getFirstName());
	    prepStmt.setString(2, user1.getLastName());
	    prepStmt.setString(3, user1.getEmail());
	    prepStmt.setString(4, user1.getPassword());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	
	 static void addClass() throws SQLException {	
			String sql = "insert into Class (classID, title, startTime, endTime, startDate, endDate, recurring)"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			//prepare statement
		    prepStmt= conn.prepareStatement(sql);
		    
		    //set the parameters 
		    prepStmt.setString(1, class1.getClassID());
		    prepStmt.setString(2, class1.getTitle());
		    prepStmt.setString(3, class1.getStartTime());
		    prepStmt.setString(4, class1.getEndTime());
		    prepStmt.setString(5, class1.getStartDate());
		    prepStmt.setString(6, class1.getEndDate());
		    prepStmt.setString(7, class1.getRecurring());
		    
		    //Execute the query
		    rs = prepStmt.executeQuery();
		   
	}
	 
	static void editClass() throws SQLException {
		//****should be able to update the other fields in the database too
		String sql = "Update Class"
				+ "set classID=?"
				+ "where uID = 20";
		
		//prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, class1.getClassID());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
		
	}
	
	static void deleteClass() throws SQLException {
		String sql = "delete from Class " 
				+ "where firstName=? and lastName=? and email=? and pass=?";
		
		  //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, user1.getFirstName());
	    prepStmt.setString(2, user1.getLastName());
	    prepStmt.setString(3, user1.getEmail());
	    prepStmt.setString(4, user1.getPassword());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	 
	static void addEvent() throws SQLException {	
		String sql = "insert into Event (title, startTime, endTime, startDate, endDate, recurring)"
				+ "values (?, ?, ?, ?, ?, ?)";
			
		//prepare statement
		prepStmt= conn.prepareStatement(sql);
		    
		//set the parameters 
		prepStmt.setString(1, event1.getTitle());
		prepStmt.setString(2, event1.getStartTime());
		prepStmt.setString(3, event1.getEndTime());
		prepStmt.setString(4, event1.getStartDate());
		prepStmt.setString(5, event1.getEndDate());
		prepStmt.setString(6, event1.getRecurring());
		    
		//Execute the query
		rs = prepStmt.executeQuery();

	}
	
	static void editEvent() throws SQLException {
		String sql = "Update Event"
				+ "set title=?, startTime=?, endTime=?, startDate=?, endDate=?, recurring=?"
				+ "where uID = 20";
				
	    //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters to the new values 
	   prepStmt.setString(1, event1.getTitle());
	   prepStmt.setString(2, event1.getStartTime());
	   prepStmt.setString(3, event1.getEndTime());
	   prepStmt.setString(4, event1.getStartDate());
	   prepStmt.setString(5, event1.getEndDate());
	   prepStmt.setString(6, event1.getRecurring());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
		
	}
	
	static void deleteEvent() throws SQLException {
		String sql = "delete from Event" 
				+ "where title=?, startTime=?, endTime=?, startDate=?, endDate=?, recurring=?";
				
		//prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, event1.getTitle());
	    prepStmt.setString(2, event1.getStartTime());
	    prepStmt.setString(3, event1.getEndTime());
	    prepStmt.setString(4, event1.getStartDate());
	    prepStmt.setString(5, event1.getEndDate());
	    prepStmt.setString(6, event1.getRecurring());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	 
	static void addToDo() throws SQLException {	
		String sql = "insert into Todo (title, due, reminder)"
				+ "values (?, ?, ?)";
					
		//prepare statement
		prepStmt= conn.prepareStatement(sql);
		    
		//set the parameters 
		prepStmt.setString(1, todo1.getTitle());
		prepStmt.setString(2, todo1.getDue());
		prepStmt.setString(3, todo1.getReminder());
		    
		//Execute the query
		rs = prepStmt.executeQuery();
	}
	
	static void editToDo() throws SQLException {
		String sql = "Update Todo"
				+ "set title=?, due=?, reminder=?"
				+ "where uID = 20";
				
	    //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters to the new values 
	    prepStmt.setString(1, todo1.getTitle());
	    prepStmt.setString(2, todo1.getDue());
	    prepStmt.setString(3, todo1.getReminder());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
		
	}
	
	static void deleteToDo() throws SQLException {
		String sql = "delete from Todo" 
				+ "where title=?, due=?, reminder=?";
				
	    //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, todo1.getTitle());
	    prepStmt.setString(2, todo1.getDue());
	    prepStmt.setString(3, todo1.getReminder());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	 
	static void addNotes() throws SQLException {	
		String sql = "insert into Notes (title, note)"
		+ "values (?, ?)";
			
		//prepare statement
		prepStmt= conn.prepareStatement(sql);
		    
		//set the parameters 
		prepStmt.setString(1, note1.getTitle());
		prepStmt.setString(2, note1.getNote());
		    
		//Execute the query
		rs = prepStmt.executeQuery();

	}
	
	static void editNotes() throws SQLException {
		String sql = "Update Notes"
		+ "set title=?, note=?"
		+ "where uID = 20";
		
	    //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters to the new values 
	    prepStmt.setString(1, note1.getTitle());
	    prepStmt.setString(2, note1.getNote());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	
	static void deleteNotes() throws SQLException {
		String sql = "delete from Notes" 
				+ "where title=?, note=?";
				
	    //prepare statement
	    prepStmt= conn.prepareStatement(sql);
	    
	    //set the parameters 
	    prepStmt.setString(1, note1.getTitle());
	    prepStmt.setString(2, note1.getNote());
	    
	    //Execute the query
	    rs = prepStmt.executeQuery();
	}
	 
}//end CanvasToo