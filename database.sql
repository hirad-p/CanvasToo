DROP DATABASE IF EXISTS canvastoo;
CREATE DATABASE canvastoo;
USE canvastoo;

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
    PRIMARY KEY (id),
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
    recurring VARCHAR(30) NOT NULL,
    created TIMESTAMP DEFAULT now(),
    modified TIMESTAMP DEFAULT now() ON UPDATE now(),
    uID MEDIUMINT,
    classID VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY (uID) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (classID) REFERENCES classes(id)
);