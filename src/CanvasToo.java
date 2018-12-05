import cli.MainCLI;
import gui.CanvasTooUI;

import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class CanvasToo {
    public static final boolean GUI_MODE = true;

    public static void main(String[] args) throws SQLException {
        if (GUI_MODE) {
            GUI();
        } else {
            CLI();
        }
    }


    private static void CLI() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        MainCLI.welcome(scanner);
    }

    private static void GUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CanvasTooUI();
            }
        });
    }
}
