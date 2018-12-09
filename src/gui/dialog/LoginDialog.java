package gui.dialog;

import gui.CanvasTooUI;
import model.User;
import storage.UserStorage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginDialog extends JDialog {
    private Container container;
    private JLabel email, pass, invalid;
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginButton, resetButton, backButton;

    private User user;
    private UserStorage storage;

    private CanvasTooUI canvas;
    private boolean success;

    public LoginDialog(CanvasTooUI canvas, JFrame parent) {
        super(parent, "Login", true);
        this.canvas = canvas;
        storage = new UserStorage();
        user = null;
        container = getContentPane();
        container.setLayout(new GridLayout(3, 2));

        // email
        email = new JLabel("* Email: ");
        emailField = new JTextField();
        container.add(email); container.add(emailField);

        // pass
        pass = new JLabel("* Password: ");
        passField = new JPasswordField();
        container.add(pass); container.add(passField);

        // buttons
        invalid = new JLabel();
        invalid.setForeground(Color.red);
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        backButton = new JButton("Back");
        backButton.addActionListener(e -> back());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton); buttonPanel.add(resetButton); buttonPanel.add(backButton);
        container.add(invalid); container.add(buttonPanel);

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

    private void login() {
        try {
            user = storage.getUser(emailField.getText(), new String(passField.getPassword()));
            success = user != null;
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
        invalid.setText("Unable to login");
        repaint();
        success = false;
    }

    private void reset() {
        invalid.setText("");
        emailField.setText("");
        passField.setText("");
    }
}
