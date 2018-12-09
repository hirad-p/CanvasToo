import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SettingsDialog extends JDialog {
    private Container container;
    private JLabel email, pass, invalid;
    private JTextField emailField;
    private JPasswordField passField;
    private JButton delButton, doneButton, resetButton;

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
        delButton = new JButton("Delete");
        delButton.addActionListener(e -> delete());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(doneButton); buttonPanel.add(resetButton); buttonPanel.add(delButton);
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
            handleInvalid("");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void delete() {
        try {
            storage.deleteUser(user);
            System.exit(0);
        } catch (SQLException e) {
            handleInvalid(e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleInvalid(String s) {
        if (s.equals("")) {
            s = "Unable to update";
        }
        invalid.setText(s);
        repaint();
        success = false;
    }

    private void reset() { ;
        emailField.setText("");
        passField.setText("");
    }
}
