# CanvasToo  By: Team Too
Hirad Pourtahmasbi | Janette Lopez Urzua | Keshuv Bagaria

### Database Schema
#### Users
```mysql-sql
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


### Functional Requirements 
Please note that `?` is an input parameter

1. A user must be able to register using an email and password.
    ```mysql-sql
    insert into users (firstName, lastName, email, pass) values (?, ?, ?, ?);
    ```

2. A user must be able to change their email/password if they desire
    ```mysql-sql
    update users set email=?, password=?  where id=?";
    ```

3. A user must be able to delete their account
    ```mysql-sql
    delete from users where id=?";
    ```

4. A user must be able to register for a class
    ```mysql-sql
    insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
        values (?, ?, ?, ?, ?, ?, ?, ?);
    ```

5. A user must be able to edit a class
    ```mysql-sql
    update classes set ?=? where id=? and uId=?;
    ```

6. A user must be able to delete a class
    ```mysql-sql
    delete from classes where id=? and uId=?;
    ```

7. 