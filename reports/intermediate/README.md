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
    ````sql
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

7. 