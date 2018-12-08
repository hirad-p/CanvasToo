package gui.dialog;

import gui.CanvasTooUI;
import model.User;
import storage.UserStorage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SettingsDialog extends JDialog {
    private Container container;
    private JLabel email, pass, invalid;
    private JTextField emailField;
    private JPasswordField passField;
    private JButton doneButton, resetButton;

    private User user;
    private UserStorage storage;

    private CanvasTooUI canvas;
    private boolean success;

    public SettingsDialog(CanvasTooUI canvas, JFrame parent) {
        super(parent, "Update", true);
        this.canvas = canvas;
        storage = new UserStorage();
        user = canvas.getUser();

        container = getContentPane();
        container.setLayout(new GridLayout(3, 2));

        // form
        //JPanel formPanel =  new JPanel();
        //formPanel.setLayout(new GridLayout(3, 2));

        email = new JLabel("Email: ");
        emailField = new JTextField();
        pass = new JLabel("Password: ");
        passField = new JPasswordField();
        invalid = new JLabel();
        invalid.setForeground(Color.red);
        container.add(email); container.add(emailField); container.add(pass); container.add(passField);
        container.add(invalid);
       // container.add(formPanel, BorderLayout.CENTER);

        // buttons
        doneButton = new JButton("Update");
        doneButton.addActionListener(e -> edit());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(doneButton); buttonPanel.add(resetButton);
        container.add(buttonPanel);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public User user() {
        return user;
    }

    private void edit() {
        if (!emailField.getText().equals("")) {
            user.setEmail(emailField.getText());
        }
        if (passField.getPassword().length != 0) {
            user.setPassword(new String(passField.getPassword()));
        }
        try {
            storage.updateUser(user);
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
        invalid.setText("Unable to update");
        repaint();
        success = false;
    }

    private void reset() { ;
        emailField.setText("");
        passField.setText("");
    }
}
