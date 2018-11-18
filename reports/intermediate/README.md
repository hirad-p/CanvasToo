# CanvasToo  By: Team Too
Hirad Pourtahmasbi | Janette Lopez Urzua | Keshuv Bagaria

### Database Schema
#### Users
```sql
DROP TABLE IF EXISTS users;
CREATE TABLE users(
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    firstName CHAR(30) NOT NULL,
    lastName CHAR(30),
    email CHAR(30) NOT NULL,
    pass CHAR(30) NOT NULL,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    PRIMARY KEY (id)
);
```
![users schema](../screenshots/users_schema.png?raw=true "Users Schema")

#### Classes
Classes get archived half a year pasted their end data
```sql
DROP TABLE IF EXISTS classes;
CREATE TABLE classes(
    id VARCHAR(10) NOT NULL,
    title VARCHAR(30),
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    recurring VARCHAR(30) NOT NULL,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    PRIMARY KEY (id, uID),
    FOREIGN KEY (uID) REFERENCES users(id) ON DELETE CASCADE
);
```
![classes schema](../screenshots/classes_schema.png?raw=true "Classes Schema")

#### Todos 
Todos in this relation will be archived once the end date of the class associated
with them passes, or if it is overdue by a certain threshold.
```sql
DROP TABLE IF EXISTS todos;
CREATE TABLE todos(
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    due TIMESTAMP,
    reminder TIMESTAMP,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY (uID) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (classID) REFERENCES classes(id)
);
```
![todos schema](../screenshots/todos_schema.png?raw=true "Todos Schema")

#### Notes
Notes in this relation will be archived once the end date of the class associated with it
```sql
DROP TABLE IF EXISTS notes;
CREATE TABLE notes(
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    note TEXT,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY (uID) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (classID) REFERENCES classes(id)
);
```
![notes schema](../screenshots/notes_schema.png?raw=true "Notes Schema")

#### Events
Event in this relation will be archived once the end date of the class associated with it,
or if the end data has passed by a certain threshold.
```sql
DROP TABLE IF EXISTS events;
CREATE TABLE events(
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    startTime TIME,
    endTime TIME,
    startDate DATE NOT NULL,
    endDate DATE,
    recurring VARCHAR(30),
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY (uID) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (classID) REFERENCES classes(id)
);
```
![events schema](../screenshots/events_schema.png?raw=true "Events Schema")


### Functional Requirements 
Please note that `?` is an input parameter

1. A user must be able to register using an email and password.
    ```sql
    insert into users (firstName, lastName, email, pass) values (?, ?, ?, ?);
    ```

2. A user must be able to change their email/password if they desire
    ```sql
    update users set email=?, password=?  where id=?";
    ```

3. A user must be able to delete their account
    ```sql
    delete from users where id=?";
    ```

4. A user must be able to register for a class
    ```sql
    insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
        values (?, ?, ?, ?, ?, ?, ?, ?);
    ```

5. A user must be able to edit a class
    ```sql
    update classes set ?=? where id=? and uId=?;
    ```

6. A user must be able to delete a class
    ```sql
    delete from classes where id=? and uId=?;
    ```

7. A user must be able to add an event 
    ```sql
    insert into events(title, startDate, endDate, uID) values(?, ?, ?, ?);
    ```

8. A user must be able to edit an event
    ```sql 
    update events 
    set ?=?
    where uID=? and id=?
    ```

9. A user must be able to delete an event
    ```sql
    delete from events
    where uID=? and title=?
    ```

10. A user must be able to add a todo
    ```sql
    insert into todos (title, due, reminder, uID, classID)
        values(?, ?, ?, ?, ?)
    ```

11. A user must be able to edit a todo
    ```sql
    update todos set ?=? where id=?;
    ```

12. A user must be able to delete a todo
    ```sql
    delete from todos
    where ?=? 
    ```

13. A user must be able to add notes 
    ```sql
    insert into notes(title, note, uId, classId)
        values(
            "project guidelines",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget sapien ante.",
            "1",
            "CS155");
    ```
    
14. A user must be able to edit notes
    ```sql
    update notes set ?=? where id=?;
    ```

15. A user must be able to delete notes
    ```sql
    delete from notes where id=?;
    ```

16. Getting how many notes a student has for each class he is in
    ```sql
    select classes.id, count(notes.id)
    from classes join notes on 
        classes.id=notes.classID and
        classes.uID = ? and
        notes.uID = ?
    group by classes.id
    ```

17. Getting a list of all events and all users attending such events
    ```sql 
    select events.title, users.firstName, users.lastName
    from users left join events on users.id=events.uID
    where title is not null
    order by title
    union all
    select events.title, users.firstName, users.lastName
    from users right join events on users.id=events.uID
    where title is not null
    order by title 
    ```
    
18. Getting all users that have 4 classes or more (full-time)
    ```sql
    select users.firstname, users.lastname 
    from users 
    where 4 <= (select COUNT(*)
                from classes 
                where classes.uID = users.id);
    ```

19. Getting all users with 0 todos
    ```sql 
    select users.firstName, users.lastName 
    from users u1
    where (u1.firstName, u1.lastName) not in
    select users.firstName, users.lastName 
    from users u2 join classes on u2.id = classes.uID 
    group by u2.id 
    having count(*) > 0);
    ```

20. Getting users  who are attending the same two classes
    ```sql
    select table1.firstname, table1.lastname 
    from (select users.id, users.firstname, users.lastname, classes.title 
    from users,classes  
    where users.id = classes.uID and classes.title =? ) as table1 
    where table1.id in (select table2.id  
    from (select users.id, users.firstname, users.lastname, classes.title 
    from users,classes  
    where users.id = classes.uID and classes.title =? ) as table2)
    ```
    
21. Getting all users with overdue todos
    ```sql
    select distinct users.firstName, users.lastName
    from users
    where id in (select uID 
                 from todos
                 where ? > due)
    ```
    
### Stored Procedure

### Triggers

### User Requests
- Function requirement 16
    Let's assume the user here, is user with Id 1.
    ```java
    public ArrayList<NoteCount> getNotesPerClass(User user) throws SQLException {
         String sql =  "select classes.id, count(notes.id) " +
                       "from classes join notes on " +
                       "classes.id=notes.classID and " +
                       "classes.uID = ? and " +
                       "notes.uID = ? " +
                       "group by classes.id";
    
         Connection conn = getConnection();
         PreparedStatement stmnt = conn.prepareStatement(sql);
         stmnt.setString(1, user.getId());
         stmnt.setString(2, user.getId());
    
         ArrayList<NoteCount> list = new ArrayList<>();
         ResultSet set = stmnt.executeQuery();
         while(set.next()) {
             list.add(new NoteCount(set.getString(1), set.getInt(2)));
         }
    
         return list;
    }
    
    public static void requirement16() throws SQLException {
        ArrayList<NoteCount> counts = notesStorage.getNotesPerClass(new User("1"));
        printNoteCounts(counts);
    }
    ```
    
    ![requirement 16](../screenshots/outputs/requirement16.png?raw=true "Requirement 16")


- Function requirement 18 
    ```java
    public ArrayList<User> getFullTimeStudents() throws SQLException {
        String sql = "select users.firstname, users.lastname " +
                     "from users " +
                     "where 4 <= (select COUNT(*) " +
                     "from classes " +
                     "where classes.uID = users.id)";
    
        ArrayList<User> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement(sql);
        ResultSet set = stmnt.executeQuery();
        while(set.next()) {
            list.add(new User(set.getString(1), set.getString(2)));
        }
    
        return list;
    }
    
    public static void requirement18() throws SQLException {
        printUsers(classStorage.getFullTimeStudents());
    }
    ```
    
    ![requirement 18](../screenshots/outputs/requirement18.png?raw=true "Requirement 18")

- Function requirement 21
    ```sql
    public ArrayList<User> getUsersWithOverDueTodos() throws SQLException {
            String sql = "select distinct users.firstName, users.lastName " +
                "from users " +
                "where id in (select uID " +
                "from todos " +
                "where ? > due)";
    
            Date date= new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
    
            Connection connection = getConnection();
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setTimestamp(1, ts);
            ResultSet set = stmnt.executeQuery();
            ArrayList<User> list = new ArrayList<>();
            while(set.next()) {
                list.add(new User(
                    set.getString(1),
                    set.getString(2)
                ));
            }
    
            connection.close();
            return list;
        }
        
    public static void requirement21() throws SQLException {
            printUsers(toDoStorage.getUsersWithOverDueTodos());
        }
    ```
    ![requirement 21](../screenshots/outputs/requirement21.png?raw=true "Requirement 21")


#### Utility Functions 
```java
public static void printUsers(ArrayList<User> users) {
    for (User u : users) {
        System.out.println(u.getFirstName() + " " + u.getLastName());
    }
}

public static void printNoteCounts(ArrayList<NoteCount> counts) {
    for (NoteCount c : counts) {
        System.out.println(c.classId + " " + c.count);
    }
}
```