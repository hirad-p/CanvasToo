package cli;

import model.LectureClass;
import model.User;
import storage.LectureClassStorage;
import utils.Printer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ClassCLI {
    public static void classesMenu(Scanner in, User user) throws SQLException {
        LectureClassStorage storage = new LectureClassStorage();

        String message = "--- Classes";
        if (user != null) {
            message += " for " + user.getFirstName();
        }
        message += " ---";
        System.out.println(message);

        ArrayList<LectureClass> classes = storage.getClasses(user);
        Printer.printClasses(classes);

        System.out.println("Please select a class below or enter [1] to add");
        String selected = in.nextLine();

        if (selected.equals("1")) {
            // @todo - client side error handling
            String id, title, startTime, endTime, startDate, endDate, recurring;
            System.out.println("Please complete the form below:");
            System.out.print("Class id: ");
            id = in.nextLine();
            System.out.print("Class title: ");
            title = in.nextLine();
            System.out.print("Start time: ");
            startTime = in.nextLine();
            System.out.print("End time: ");
            endTime = in.nextLine();
            System.out.print("Start date: ");
            startDate = in.nextLine();
            System.out.print("End date: ");
            endDate = in.nextLine();
            System.out.print("Recurring: ");
            recurring = in.nextLine();
            LectureClass toAdd = new LectureClass(id, title, startTime, endTime, startDate, endDate, recurring);
            storage.addClass(toAdd, user);
            selected = toAdd.getClassID();
        }

        Comparator<LectureClass> c = LectureClass.getComparator();
        int index = Collections.binarySearch(classes, new LectureClass(selected), c);
        if (index < 0) {
            System.out.println("Invalid option");
            return;
        }

        LectureClass lectureClass = classes.get(index);
        System.out.print("------ Options for "); Printer.printClass(lectureClass);
        // @todo - show notes, todos, and events;
    }
}
