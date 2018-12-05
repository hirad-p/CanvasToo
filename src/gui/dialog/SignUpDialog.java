package gui.dialog;

import gui.CanvasTooUI;
import model.User;
import storage.UserStorage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SignUpDialog extends JDialog {
    private Container container;
    private JLabel first, last, email, pass, invalid;
    private JTextField firstField, lastField, emailField;
    private JPasswordField passField;
    private JButton loginButton, resetButton;

    private User user;
    private UserStorage storage;

    private CanvasTooUI canvas;
    private boolean success;

    public SignUpDialog(CanvasTooUI canvas, JFrame parent) {
        super(parent, "Login", true);
        this.canvas = canvas;
        storage = new UserStorage();
        user = null;

        container = getContentPane();
        container.setLayout(new BorderLayout());

        // form
        JPanel formPanel =  new JPanel();
        formPanel.setLayout(new GridLayout(5, 2));

        first = new JLabel("First Name: ");
        firstField = new JTextField();
        last = new JLabel("Last Name: ");
        lastField = new JTextField();
        email = new JLabel("Email: ");
        emailField = new JTextField();
        pass = new JLabel("Password: ");
        passField = new JPasswordField();
        invalid = new JLabel();
        invalid.setForeground(Color.red);
        formPanel.add(first); formPanel.add(firstField); formPanel.add(last); formPanel.add(lastField);
        formPanel.add(email); formPanel.add(emailField); formPanel.add(pass); formPanel.add(passField);
        formPanel.add(invalid);
        container.add(formPanel, BorderLayout.CENTER);

        // buttons
        loginButton = new JButton("Signup");
        loginButton.addActionListener(e -> signUp());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton); buttonPanel.add(resetButton);
        container.add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean success() {
        return this.success;
    }

    public User user() {
        return user;
    }

    private void signUp() {
        user = new User(firstField.getText(), lastField.getText(), emailField.getText(), new String(passField.getPassword()));
        try {
            storage.addUser(user);
            success = user.getId() != null;
            if (success) {
                canvas.setUser(user);
                dispose();
            }
            handleInvalid();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void handleInvalid() {
        invalid.setText("Unable to sign up.");
        repaint();
        success = false;
    }

    private void reset() {
        invalid.setText("");
        firstField.setText("");
        lastField.setText("");
        emailField.setText("");
        passField.setText("");
    }
}
