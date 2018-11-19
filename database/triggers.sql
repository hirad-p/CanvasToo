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
