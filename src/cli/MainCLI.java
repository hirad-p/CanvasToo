package cli;

import java.sql.SQLException;
import java.util.Scanner;

public class MainCLI {
    public static void welcome(Scanner in) throws SQLException {
        System.out.println("Welcome to CanvasToo");
        System.out.println("Please select from the following options:");
        System.out.println("[1] Login");
        System.out.println("[2] Sign up");
        System.out.println("[3] Admin");
        System.out.println("[4] Exit");


        String input;
        boolean cont = true;
        while (cont) {
            input = in.nextLine();
            switch (input) {
                case "1":
                    UserCLI.login(in);
                    cont = false;
                    break;
                case "2":
                    UserCLI.signup(in);
                    cont = false;
                    break;
                case "3":
                    UserCLI.admin(in);
                    cont = false;
                    break;
                case "4":
                    exit();
                    cont = false;
                    break;
                default:
                    System.out.println("Please enter a valid option");

            }
        }
    }

    public static void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
