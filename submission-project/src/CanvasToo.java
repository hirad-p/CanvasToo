
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.SwingUtilities;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CanvasToo {
    public static final boolean GUI_MODE = true;

    public static void main(String[] args) throws SQLException {
    	System.out.print("Hello to canvas too!");
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
