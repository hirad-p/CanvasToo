import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CanvasTooUI {
    public JFrame frame;
    private User user;
    private JPanel welcomePanel, adminPanel, menuPanel;

    public CanvasTooUI() {
        frame = new JFrame("CanvasToo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        welcomeScreen();
        
        frame.pack();
        frame.setVisible(true);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void welcomeScreen() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        // welcome text
        JLabel welcomeLabel = new JLabel("Welcome to CanvasToo!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        //welcomeLabel.setForeground(Color.RED);
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

        //logo
        ImageIcon logo = new ImageIcon("src/CanvasTooLogo.jpg");
        JLabel logoLabel = new JLabel(logo);
        welcomePanel.add(logoLabel, BorderLayout.CENTER);
        
        
        JButton signup, login, admin;
        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        signup = new JButton("Sign up");
        signup.addActionListener(e -> {
            SignUpDialog signUpDialog = new SignUpDialog(this, frame);
            signUpDialog.setVisible(true);
            if (user != null && user.getId() != null) {
                frame.remove(buttonPanel);
                mainMenu();
            }
        });
        login = new JButton("Login");
        login.addActionListener(e -> {
            LoginDialog loginDialog = new LoginDialog(this, frame);
            loginDialog.setVisible(true);
            if (user != null && user.getId() != null) {
                frame.remove(buttonPanel);
                mainMenu();
            }
        });
        admin = new JButton("Admin");
        admin.addActionListener(e -> {
            System.out.println("Admin Button");
            adminScreen();
        });
        JButton[] buttons = {signup, login, admin};
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);
        changeScreen(welcomePanel, 300, 75);
    }

    private void mainMenu() {
        menuPanel = new JPanel();
        //menuPanel.setLayout(new GridLayout(2, 1));
        menuPanel.setLayout(new BorderLayout());
        
        JButton classBtn, notesBtn, todoBtn, eventsBtn, settingsBtn, LogOutBtn;
        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 0, 10));


        classBtn = new JButton("Classes");
        classBtn.addActionListener(e -> {
            System.out.println("Clicked on classes");
            ClassesDisplayer classesDisplayer = new ClassesDisplayer(this);
            classesDisplayer.setVisible(true);
        });

        notesBtn = new JButton("Notes");
        notesBtn.addActionListener(e -> {
            System.out.println("Clicked on notes");
            NotesDisplayer notesDisplayer = new NotesDisplayer(this);
            notesDisplayer.setVisible(true);
        });

        todoBtn = new JButton("Todos");
        todoBtn.addActionListener(e -> {
            System.out.println("Clicked on todos");
            TodoDisplayer todoDisplayer = new TodoDisplayer(this);
            todoDisplayer.setVisible(true);
        });

        eventsBtn = new JButton("Events");
        eventsBtn.addActionListener(e -> {
            System.out.println("Clicked on events");
            EventDisplayer eventDisplayer = new EventDisplayer(this);
            eventDisplayer.setVisible(true);
        });

        settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener(e -> {
            System.out.println("Clicked on settings");
            SettingsDialog settings = new SettingsDialog(this, frame);
            settings.setVisible(true);
        });
        
        LogOutBtn = new JButton("Log Out");
        LogOutBtn.addActionListener(e -> {
            System.out.println("Clicked on Log Out");
            welcomeScreen();
        });

       // GridBagConstraints gbc = new GridBagConstraints();
       //gbc.gridwidth = GridBagConstraints.REMAINDER;
       // gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton[] buttons = {classBtn, notesBtn, todoBtn, eventsBtn, settingsBtn, LogOutBtn};
        for (JButton b : buttons) {
            //buttonPanel.add(b, gbc);
            buttonPanel.add(b);
        }

        JLabel welcomeLabel = new JLabel("Hello, " + user.getFirstName());
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(welcomeLabel);
        menuPanel.add(welcomePanel, BorderLayout.NORTH);
        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        //changeScreen(menuPanel, 250, 300);
        changeScreen(menuPanel, 250, 300);
    }

    private void adminScreen() {

        JPanel adminScreen = new JPanel();
        adminScreen.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        JLabel inputLabel = new JLabel("Input: ");
        JTextField inputArea = new JTextField("", 25);
        infoPanel.add(inputLabel);
        infoPanel.add(inputArea);

        JTextArea resultArea = new JTextArea(25, 100);
        resultArea.setEditable(false);

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));

        JButton fullTimeStudents = new JButton("Full Time Students");
        fullTimeStudents.addActionListener(e -> {
            LectureClassStorage storage = new LectureClassStorage();

            try {
                ArrayList<User> users = storage.getFullTimeStudents();
                if (users.size() > 0) {
                    resultArea.setText(Printer.printUsers(users));
                } else {
                    resultArea.setText("No full time users");
                }
            } catch (SQLException exception) {
                resultArea.setText(exception.getMessage());
            }
        });

        JButton usersFromClasses = new JButton("Users From Classes");
        usersFromClasses.addActionListener(e -> {
            if (inputArea.getText().equals("")) {
                resultArea.setText("Please enter a comma separated list of 2 class ids. Ex: CS 155, CS 157A");
                return;
            }

            LectureClassStorage storage = new LectureClassStorage();
            String input = inputArea.getText();
            String[] classIds = input.split(",");

            try {
                ArrayList<User> users = storage.getUsersFromClasses(classIds[0], classIds[1]);
                if (users.size() > 0) {
                    resultArea.setText(Printer.printUsers(users));
                } else {
                    resultArea.setText("No users from the selected classes");
                }
            } catch (SQLException exception) {
                resultArea.setText(exception.getMessage());
            }
        });

        JButton usersWithNoTodos = new JButton("Users w/ No Todos");
        usersWithNoTodos.addActionListener(e -> {
            ToDoStorage storage = new ToDoStorage();

            try {
                ArrayList<User> users = storage.getUsersWithNoToDos();
                if (users.size() > 0) {
                    resultArea.setText(Printer.printUsers(users));
                } else {
                    resultArea.setText("No users with no todos");
                }
            } catch (SQLException exception) {
                resultArea.setText(exception.getMessage());
            }
        });

        JButton usersWithOverdueTodos = new JButton("Users w/ Overdue Todos");
        usersWithOverdueTodos.addActionListener(e -> {
            ToDoStorage storage = new ToDoStorage();

            try {
                ArrayList<User> users = storage.getUsersWithOverDueTodos();
                if (users.size() > 0) {
                    resultArea.setText(Printer.printUsers(users));
                } else {
                    resultArea.setText("No users with overdue todos");
                }
            } catch (SQLException exception) {
                resultArea.setText(exception.getMessage());
            }
        });

        JButton archive = new JButton("Archive");
        archive.addActionListener(e -> {
            if (inputArea.getText().equals("")) {
                resultArea.setText("Please enter a timestamp (yyyy-mm-dd hh:mm:ss)");
                return;
            }

            Storage storage = new Storage();
            try {
                storage.archive(inputArea.getText());
                resultArea.setText("Done!");
            } catch (SQLException exception) {
                resultArea.setText(exception.getMessage());
            }
        });

        options.add(fullTimeStudents);
        options.add(usersFromClasses);
        options.add(usersWithNoTodos);
        options.add(usersWithOverdueTodos);
        options.add(archive);

        adminScreen.add(infoPanel, BorderLayout.NORTH);
        adminScreen.add(resultArea, BorderLayout.CENTER);
        adminScreen.add(options, BorderLayout.SOUTH);

        changeScreen(adminScreen, 500, 400);
    }

    private void changeScreen(JComponent component, int width, int height) {
        frame.setContentPane(component);
        frame.setSize(width, height);
        frame.pack();
        frame.validate();
        frame.repaint();
    }
}
