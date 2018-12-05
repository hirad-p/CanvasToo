package cli;

import model.LectureClass;
import model.ToDo;
import model.User;
import storage.ToDoStorage;
import utils.Printer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoCLI {
    public static void todoMenu(Scanner in, User user, LectureClass lectureClass) throws SQLException {
        ToDoStorage storage = new ToDoStorage();
        ArrayList<ToDo> todos = storage.getTodos(user, lectureClass);
        Printer.printTodos(todos);

        System.out.println("Please enter a number to mark as done or [0] to create new");

        String input;
        boolean cont = true;
        while (cont) {
            input = in.nextLine();
            if (input == "0") {
                // @todo - add to do
                todoMenu(in, user, lectureClass);
            } else if (Integer.valueOf(input) > todos.size()) {
                System.out.println("Invalid option");
            } else {
                // @todo - edit/delete to do
            }

        }
    }
}
