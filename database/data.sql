--users
insert into users (firstName, lastName, email, pass)
    values ("Hirad", "P", "hirad@p.com", "test1");
insert into users (firstName, lastName, email, pass)
    values ("Janette", "U", "janette@u.com", "test2");
insert into users (firstName, lastName, email, pass)
    values ("Keshuv", "B", "keshuv@b.com", "test3");
insert into users (firstName, lastName, email, pass)
    values ("Nick", "H", "nick@h.com", "test4");

--class
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

--todos
insert into todos (title, due, reminder, uID, classID)
    values("finish readme updates", '2018-11-19 10:30:00', '2018-11-18 20:00:00', 1, "CS157A");
insert into todos (title, due, reminder, uID, classID)
    values("finish sql statements", '2018-11-19 10:30:00', '2018-11-18 16:00:00', 2, "CS157A");
insert into todos (title, due, reminder, uID, classID)
    values("make triggers", '2018-11-19 10:30:00', '2018-11-18 13:00:00', 3, "CS157A");

insert into todos (title, due, reminder, uID, classID)
    values("study for midterm", '2018-11-19 12:00:00', '2018-11-18 08:00:00', 1, "CS155");

--notes
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

--events
insert into events(title, startDate, endDate, uID)
    values("Thanksgiving Break", "2018-11-21", "2018-11-25", 1);

insert into events(title, startTime, endTime, startDate, endDate, recurring, classId)
    values("Office hours", "9:20:00", "10:20:00", "2018-8-22", "2018-12-10", "TR", "CS157A");