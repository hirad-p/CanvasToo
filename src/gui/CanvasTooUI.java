package gui;

import gui.dialog.LoginDialog;
import gui.dialog.SettingsDialog;
import gui.dialog.SignUpDialog;
import gui.diplayer.ClassesDisplayer;
import gui.diplayer.NotesDisplayer;
import gui.diplayer.TodoDisplayer;
import model.User;

import javax.swing.*;
import java.awt.*;

public class CanvasTooUI {
    public JFrame frame;
    private User user;
    private JPanel welcomePanel, adminPanel, menuPanel;

    public CanvasTooUI() {
        frame = new JFrame("CanvasToo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomeScreen();

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
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

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
        welcomePanel.add(buttonPanel, BorderLayout.PAGE_END);
        changeScreen(welcomePanel, 300, 75);
    }

    private void mainMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 1));

        JButton classBtn, notesBtn, todoBtn, eventsBtn, settingsBtn;
        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());


        classBtn = new JButton("Classes");
        classBtn.addActionListener(e -> {
            System.out.println("Clicked on classes");
            ClassesDisplayer classesDisplayer = new ClassesDisplayer(this);
            classesDisplayer.setVisible(true);
        });

        notesBtn = new JButton("Note");
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
        });

        settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener(e -> {
            System.out.println("Clicked on settings");
            SettingsDialog settings = new SettingsDialog(this, frame);
            settings.setVisible(true);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton[] buttons = {classBtn, notesBtn, todoBtn, eventsBtn, settingsBtn};
        for (JButton b : buttons) {
            buttonPanel.add(b, gbc);
        }

        JLabel welcomeLabel = new JLabel("Hello, " + user.getFirstName());
        menuPanel.add(welcomeLabel, BorderLayout.NORTH);
        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        changeScreen(menuPanel, 250, 300);
    }

    private void adminScreen() {
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
    }

    private void changeScreen(JComponent component, int width, int height) {
        frame.setContentPane(component);
        frame.setSize(width, height);
        frame.validate();
        frame.repaint();
    }
}
