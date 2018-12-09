-- All other .sql files combined in order
-- to get application running - run this file

-- Database
DROP DATABASE IF EXISTS canvastoo;
CREATE DATABASE canvastoo;
USE canvastoo;

-- Tables
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

-- Archives
DROP TABLE IF EXISTS classesarchive;
CREATE TABLE classesarchive(
    id VARCHAR(10) NOT NULL,
    title VARCHAR(30),
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    recurring VARCHAR(30) NOT NULL,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT
);

DROP TABLE IF EXISTS todosarchive;
CREATE TABLE todosarchive(
    id MEDIUMINT NOT NULL,
    title VARCHAR(30) NOT NULL,
    due TIMESTAMP,
    reminder TIMESTAMP,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10)
);

DROP TABLE IF EXISTS notesarchive;
CREATE TABLE notesarchive(
    id MEDIUMINT NOT NULL,
    title VARCHAR(30) NOT NULL,
    note TEXT,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10)
);

DROP TABLE IF EXISTS eventsarchive;
CREATE TABLE eventsarchive(
    id MEDIUMINT NOT NULL,
    title VARCHAR(30) NOT NULL,
    startTime TIME,
    endTime TIME,
    startDate DATE NOT NULL,
    endDate DATE,
    recurring VARCHAR(30),
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10)
);

-- procedure
DROP PROCEDURE IF EXISTS getToDoByClass;
DROP PROCEDURE IF EXISTS getNotesbyTitle;

DELIMITER $$
CREATE PROCEDURE getToDoByClass(IN class VARCHAR(10))
BEGIN
SELECT *
FROM todos
WHERE classID = class;
END $$
CREATE PROCEDURE getNotesbyTitle(IN noteTitle VARCHAR(30))
BEGIN
SELECT *
FROM notes
WHERE title = noteTitle;
END $$
DELIMITER ;

-- triggers
DROP TRIGGER IF EXISTS deleteUser;
DELIMITER $$
CREATE TRIGGER deleteUser
AFTER delete ON users
for each row
begin
delete from classes where uID = old.id;
delete from todos where uID = old.id;
delete from notes where uID = old.id;
delete from events where uID = old.id;
end $$
DELIMITER ;


DROP TRIGGER IF EXISTS firstAndLast;
DELIMITER $$
Create trigger firstAndLast
AFTER insert on classes
for each row
begin
insert into events (title, startTime, endTime, startdate, endDate, recurring, uid, classID)
values ("First Lecture", new.startTime, new.endTime, new.startDate, new.startDate, null,  new.uID, new.id);
insert into events (title, startTime, endTime, startdate, endDate, recurring, uid, classID)
values ("Last Lecture", new.startTime, new.endTime, new.endDate, new.endDate, null,  new.uID, new.id);
end $$
DELIMITER ;

-- data
-- users
insert into users (firstName, lastName, email, pass)
values ("Hirad", "P", "hirad@p.com", "test1");
insert into users (firstName, lastName, email, pass)
values ("Janette", "U", "janette@u.com", "test2");
insert into users (firstName, lastName, email, pass)
values ("Keshuv", "B", "keshuv@b.com", "test3");
insert into users (firstName, lastName, email, pass)
values ("Nick", "H", "nick@h.com", "test4");

-- class
insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS157A", "Databases", "10:30:00", "11:45:00", "2018-8-22", "2018-12-10", "MW", 1);
insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS157A", "Databases", "10:30:00", "11:45:00", "2018-8-22", "2018-12-10", "MW", 2);
insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS157A", "Databases", "10:30:00", "11:45:00", "2018-8-22", "2018-12-10", "MW", 3);
insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS157A", "Databases", "10:30:00", "11:45:00", "2018-8-22", "2018-12-10", "MW", 4);

insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS158B", "Networks", "13:30:00", "14:45:00", "2018-8-22", "2018-12-10", "MW", 1);
insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS158B", "Networks", "13:30:00", "14:45:00", "2018-8-22", "2018-12-10", "MW", 4);

insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS166", "Security", "15:00:00", "16:15:00", "2018-8-23", "2018-12-11", "TR", 1);

insert into classes (id, title, startTime, endTime, startDate, endDate, recurring, uID)
values ("CS155", "Algorithms", "12:00:00", "13:15:00", "2018-8-22", "2018-12-10", "MW", 1);

-- todos
insert into todos (title, due, reminder, uID, classID)
values("finish readme updates", '2018-11-19 10:30:00', '2018-11-18 20:00:00', 1, "CS157A");
insert into todos (title, due, reminder, uID, classID)
values("finish sql statements", '2018-11-19 10:30:00', '2018-11-18 16:00:00', 2, "CS157A");
insert into todos (title, due, reminder, uID, classID)
values("make triggers", '2018-11-19 10:30:00', '2018-11-18 13:00:00', 3, "CS157A");

insert into todos (title, due, reminder, uID, classID)
values("study for midterm", '2018-11-19 12:00:00', '2018-11-18 08:00:00', 1, "CS155");

-- notes
insert into notes(title, note, uId, classId)
values(
"lecture 1",
"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget sapien ante.",
"1",
"CS157A");

insert into notes(title, note, uId, classId)
values(
"Midterm review",
"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget sapien ante.",
"2",
"CS157A");

insert into notes(title, note, uId, classId)
values(
"project guidelines",
"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget sapien ante.",
"1",
"CS155");

-- events
insert into events(title, startDate, endDate, uID)
values("Thanksgiving Break", "2018-11-21", "2018-11-25", 1);

insert into events(title, startTime, endTime, startDate, endDate, recurring, classId)
values("Office hours", "9:20:00", "10:20:00", "2018-8-22", "2018-12-10", "TR", "CS157A");

-- archive procedure
DROP PROCEDURE IF EXISTS archiveRows;

DELIMITER $$
CREATE PROCEDURE archiveRows(IN d TIMESTAMP)
BEGIN
  INSERT INTO classesarchive
    SELECT * FROM classes c where c.modified < d;

  DELETE FROM classes WHERE modified < d;

  INSERT INTO todosarchive
    SELECT * FROM todos t where t.modified < d;

  DELETE FROM todos WHERE modified < d;

  INSERT INTO notesarchive
    SELECT * FROM notes n where n.modified < d;

  DELETE FROM notes WHERE modified < d;

   INSERT INTO eventsarchive
    SELECT * FROM events e where e.modified < d;

  DELETE FROM events WHERE modified < d;
END $$
DELIMITER ;
