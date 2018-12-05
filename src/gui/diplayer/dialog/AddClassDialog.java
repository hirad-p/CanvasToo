package gui.diplayer.dialog;

import gui.diplayer.ClassesDisplayer;
import model.LectureClass;
import model.User;
import storage.LectureClassStorage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddClassDialog extends JDialog {
    private Container container;
    private JLabel idL, titleL, startTimeL, endTimeL, startDateL, endDateL, recurringL, invalid;
    private JTextField  id, title, startTime, endTime, startDate, endDate, recurring;
    private JButton addButton, resetButton;

    private User user;
    private LectureClassStorage lectureClassStorage;
    private ClassesDisplayer displayer;

    public AddClassDialog(ClassesDisplayer displayer) {
        super(displayer.canvas.frame, "Login", true);
        this.displayer = displayer;
        user = displayer.canvas.getUser();
        lectureClassStorage = new LectureClassStorage();

        container = getContentPane();
        container.setLayout(new GridLayout(8, 2));

        idL = new JLabel("Id: ");
        id = new JTextField();
        container.add(idL); container.add(id);

        titleL = new JLabel("Title: ");
        title = new JTextField();
        container.add(titleL); container.add(title);

        startTimeL = new JLabel("Start Time: ");
        startTime = new JTextField();
        container.add(startTimeL); container.add(startTime);

        endTimeL = new JLabel("End Time: ");
        endTime = new JTextField();
        container.add(endTimeL); container.add(endTime);

        startDateL = new JLabel("Start Date: ");
        startDate = new JTextField();
        container.add(startDateL); container.add(startDate);

        endDateL = new JLabel("End Date: ");
        endDate = new JTextField();
        container.add(endDateL); container.add(endDate);

        // @todo - switch to radio buttons
        recurringL = new JLabel("Recurring: ");
        recurring = new JTextField();
        container.add(recurringL); container.add(recurring);

        // buttons
        invalid = new JLabel();
        invalid.setForeground(Color.red);
        addButton = new JButton("Add");
        addButton.addActionListener(e -> add());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton); buttonPanel.add(resetButton);
        container.add(invalid); container.add(buttonPanel);

        pack();
        setResizable(false);
        setLocationRelativeTo(displayer.canvas.frame);
    }

    public User user() {
        return user;
    }

    private void add() {
        try {
            LectureClass c = new LectureClass(
                    id.getText(),
                    title.getText(),
                    startTime.getText(),
                    endTime.getText(),
                    startDate.getText(),
                    endDate.getText(),
                    recurring.getText()
            );
            lectureClassStorage.addClass(c, user);
            this.displayer.AddClass(c);
        } catch (SQLException exception) {
            handleInvalid();
            exception.printStackTrace();
        }
    }

    private void handleInvalid() {
        invalid.setText("Unable to add class");
        repaint();
    }

    private void reset() {
        id.setText("");
        title.setText("");
        startTime.setText("");
        endTime.setText("");
        startDate.setText("");
        endDate.setText("");
        recurring.setText("");
    }
}
