import java.util.ArrayList;

public class Printer {
    public static String printUsers(ArrayList<User> users) {
        User u;
        String result = "";
        for (int i = 0; i < users.size(); i++) {
            u = users.get(i);
            result += Integer.toString(i + 1) + ". " + u.getFirstName() + " " + u.getLastName() + "\n";
        }

        return result;
    }

    public static void printClasses(ArrayList<LectureClass> classes) {
        for (LectureClass c : classes) {
           printClass(c);
        }
    }

    public static void printClass(LectureClass c) {
        System.out.println(c.getClassID() + " - " + c.getTitle());
    }

    public static void printNoteCounts(ArrayList<Count> counts) {
        for (Count c : counts) {
            System.out.println(c.id + " " + c.count);
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
