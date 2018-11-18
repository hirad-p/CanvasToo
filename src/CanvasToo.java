import model.LectureClass;
import model.User;
import storage.LectureClassStorage;
import storage.ToDoStorage;
import storage.UserStorage;

import java.sql.SQLException;
import java.util.ArrayList;

public class CanvasToo {
    static ToDoStorage toDoStorage;
    static LectureClassStorage classStorage;
    static UserStorage userStorage;

    public static void main(String[] args) throws SQLException {
        toDoStorage = new ToDoStorage();
        classStorage = new LectureClassStorage();
        userStorage = new UserStorage();

        // requirement4();
        // requirement5();
        requirement6();

//        // add a test user
//        User user = new User("Hirad", "P", "test@test.com", "test");
//        userStorage.addUser(user);
//
//        // update a test user
//        user.setId("8");
//        user.setEmail("1@1.com");
//        user.setPassword("test2");
//        userStorage.updateUser(user);
//
//        // delete a user
//        userStorage.deleteUser(user);

        // function requirement 19
        // Getting users with 0 todos
//        printUsers(toDoStorage.getUsersWithNoToDos());
//
//        // function requirement 20
//        // Getting users who are attending the same number of classes
//        LectureClass[] classes = {
//            new LectureClass("CS158"),
//            new LectureClass("KIN19A"),
//        };
//        printUsers(classStorage.getUsersFromClasses(classes));
    }

    // function requirement 4
    // add a user to a class
    public static void requirement4() throws SQLException {
        LectureClass lectureClass = new LectureClass(
            "CS157A",
            "Database Management Systems",
            "10:30:00",
            "11:45:00",
            "2018-8-22",
            "2018-12-12",
            "MW"
        );
        User user = new User("Hirad", "P", "hirad@p.com");
        user.setId("5");

        classStorage.addClass(lectureClass, user);
    }

    // function requirement 5
    // edit a class
    public static void requirement5() throws SQLException {
        LectureClass lectureClass = new LectureClass("CS157A");
        User user = new User("Hirad", "P", "hirad@p.com");
        user.setId("5");

        classStorage.editClass(lectureClass, user, "recurring", "TR");
    }

    public static void requirement6() throws SQLException {
        classStorage.deleteClass("CS158", "5");
    }

    public static void printUsers(ArrayList<User> users) {
        for (User u : users) {
            System.out.println(u.getFirstName() + " " + u.getLastName());
        }
    }
}
