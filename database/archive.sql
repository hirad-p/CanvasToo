DROP PROCEDURE IF EXISTS archiveRows;

DELIMITER $$
CREATE PROCEDURE archiveRows(IN d TIMESTAMP)
BEGIN
  INSERT INTO todosarchive
    SELECT * FROM todos t where t.modified < d;
  DELETE FROM todos WHERE modified < d;

  INSERT INTO notesarchive
    SELECT * FROM notes n where n.modified < d;
  DELETE FROM notes WHERE modified < d;

  INSERT INTO eventsarchive
	SELECT * FROM events e where e.modified < d;
  DELETE FROM events WHERE modified < d;
  
  INSERT INTO classesarchive
    SELECT * FROM classes c where c.modified < d;
  DELETE FROM classes WHERE modified < d;
END $$
DELIMITER ;
