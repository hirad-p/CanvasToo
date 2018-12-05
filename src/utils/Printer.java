package utils;

import model.LectureClass;
import model.NoteCount;
import model.ToDo;
import model.User;

import java.util.ArrayList;

public class Printer {
    public static void printUsers(ArrayList<User> users) {
        System.out.println("--- Users ---");
        for (User u : users) {
            System.out.println(u.getFirstName() + " " + u.getLastName());
        }
    }

    public static void printClasses(ArrayList<LectureClass> classes) {
        for (LectureClass c : classes) {
           printClass(c);
        }
    }

    public static void printClass(LectureClass c) {
        System.out.println(c.getClassID() + " - " + c.getTitle());
    }

    public static void printNoteCounts(ArrayList<NoteCount> counts) {
        for (NoteCount c : counts) {
            System.out.println(c.classId + " " + c.count);
        }
    }

    public static void printTodos(ArrayList<ToDo> todos) {
        System.out.println("--- Todos ---");
        int index = 1;
        for (ToDo todo : todos) {
            System.out.print(index + ". "); printTodo(todo);
            index++;
        }

    }

    public static void printTodo(ToDo todo) {
        // @todo - add indicator if it's past due
        String s = "[ ] " + todo.getTitle();
        if (todo.getClassId() != null) {
            s += " for " + todo.getClassId();
        }

        if (todo.getDue() != null) {
            s += " due " + todo.getDue();
        }

        if (todo.getReminder() != null) {
            s += " for " + todo.getReminder();
        }

        System.out.println(s);
    }
}
