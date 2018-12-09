import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserCLI {
    public static void login(Scanner in) throws SQLException {
        String email, pass;
        boolean cont = true;
        while (cont) {
            System.out.print("Email: ");
            email = in.nextLine();
            System.out.println("Password: ");
            pass = in.nextLine();

            System.out.println("Logging in...");
            // get user from database
            User user = (new UserStorage()).getUser(email, pass);
            if (user != null) {
                mainmenu(in, user);
                cont = false;
            } else {
                System.out.println("Invalid email and/or password. Please try again.");
            }
        }

    }

    public static void signup(Scanner in) throws SQLException {
        System.out.println("Thanks for signing up!");

        String first, last, email, pass;
        System.out.println("Please complete the form below: ");
        System.out.print("First name: ");
        first = in.nextLine();
        System.out.print("Last name: ");
        last = in.nextLine();
        System.out.print("Email: ");
        email = in.nextLine();
        System.out.print("Password: ");
        pass = in.nextLine();

        User user = new User(first, last, email, pass);
        new UserStorage().addUser(user);
        mainmenu(in, user);
    }

    public static void admin(Scanner in) throws SQLException {
        System.out.println("Logging in as Admin...");
        System.out.println("Please select from the options below: ");

        System.out.println("[1] Full time students");
        System.out.println("[0] Back");

        String input;
        boolean cont = true;
        while (cont) {
            input = in.nextLine();
            switch (input) {
                case "1":
                    ArrayList<User> users = (new LectureClassStorage()).getFullTimeStudents();
                    System.out.println("Full time students");
                    Printer.printUsers(users);
                    return;
                case "0":
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        }

    }

    public static void mainmenu(Scanner in, User user) throws SQLException {
        System.out.println("Welcome " + user.getFirstName() + "!");
        System.out.println("Please select from the following options:");
        System.out.println("[1] Classes");
        System.out.println("[2] Note");
        System.out.println("[3] Todos");
        System.out.println("[4] Events");
        System.out.println("[5] Settings");
        System.out.println("[6] Exit");

        String input;
        boolean cont = true;
        while (cont) {
            input = in.nextLine();
            switch (input) {
                case "1":
                    ClassCLI.classesMenu(in, user);
                    cont = false;
                    break;
                case "2":
                    cont = false;
                    break;
                case "3":
                    TodoCLI.todoMenu(in, user, null);
                    cont = false;
                    break;
                case "4":
                    cont = false;
                    break;
                case "5":
                    setting(in, user);
                    break;
                case "6":
                    MainCLI.exit();
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }

    public static void setting(Scanner in, User user) throws SQLException {
        System.out.println("Settings for " + user.getFirstName());

        String input;
        boolean cont = true;
        while (cont) {
            input = in.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Please enter new information: ");
                    System.out.print("Email: ");
                    user.setEmail(in.nextLine());
                    System.out.print("Password: ");
                    user.setPassword(in.nextLine());
                    System.out.println("Updating...");
                    new UserStorage().updateUser(user);
                    System.out.println("Done!");
                    cont = false;
                    break;
                case "2":
                    System.out.println("Deleting user...");
                    new UserStorage().deleteUser(user);
                    System.out.println("Done!");
                    MainCLI.welcome(in);
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }
}
