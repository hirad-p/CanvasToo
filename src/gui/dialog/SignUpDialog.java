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
    private JButton loginButton, resetButton, backButton;

    private User user;
    private UserStorage storage;

    private CanvasTooUI canvas;
    private boolean success;

    public SignUpDialog(CanvasTooUI canvas, JFrame parent) {
        super(parent, "Sign Up", true);
        this.canvas = canvas;
        storage = new UserStorage();
        user = null;

        container = getContentPane();
        //container.setLayout(new BorderLayout());
        container.setLayout(new GridLayout(5, 2));
        
        // form
        //JPanel formPanel =  new JPanel();
        //formPanel.setLayout(new GridLayout(5, 2));

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
        container.add(first); container.add(firstField); container.add(last); container.add(lastField);
        container.add(email); container.add(emailField); container.add(pass); container.add(passField);
        container.add(invalid);
        //container.add(formPanel, BorderLayout.CENTER);

        // buttons
        loginButton = new JButton("Sign Up");
        loginButton.addActionListener(e -> signUp());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        backButton = new JButton("Back");
        backButton.addActionListener(e -> back());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton); buttonPanel.add(resetButton); buttonPanel.add(backButton);
        container.add(buttonPanel);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void back() {
		// TODO Auto-generated method stub
		dispose();
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
