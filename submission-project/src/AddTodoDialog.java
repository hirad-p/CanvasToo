import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddTodoDialog extends JDialog {
    private Container container;
    private JLabel titleL, classL, reminderL, dueL, invalid;
    private JTextField  title, reminder, due;
    private JComboBox<String> classIds;
    private JButton addButton, resetButton, backButton;

    private User user;
    private ToDoStorage storage;
    private TodoDisplayer displayer;

    public AddTodoDialog(TodoDisplayer displayer, ArrayList<LectureClass> classes) {
        super(displayer.canvas.frame, "Add Todo", true);
        this.displayer = displayer;
        user = displayer.canvas.getUser();

        storage = new ToDoStorage();

        container = getContentPane();
        container.setLayout(new GridLayout(5, 2));

        titleL = new JLabel("* Title: ");
        title = new JTextField();
        container.add(titleL); container.add(title);

        // @todo add none as option
        classL = new JLabel("* Class: ");
        String[] ids = new String[classes.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = classes.get(i).getClassID();
        }
        classIds = new JComboBox<>(ids);
        container.add(classL); container.add(classIds);

        // @todo better input type
        reminderL = new JLabel("Reminder (Format: yyyy-mm-dd hh:mm:ss): ");
        reminder = new JTextField();
        container.add(reminderL); container.add(reminder);

        dueL = new JLabel("Due (Format: yyyy-mm-dd hh:mm:ss): ");
        due = new JTextField();
        container.add(dueL); container.add(due);

        // buttons
        invalid = new JLabel();
        invalid.setForeground(Color.red);
        addButton = new JButton("Add");
        addButton.addActionListener(e -> add());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        addButton.addActionListener(e -> add());
        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton); buttonPanel.add(resetButton); buttonPanel.add(backButton);
        container.add(invalid); container.add(buttonPanel);

        pack();
        setResizable(false);
        setLocationRelativeTo(displayer.canvas.frame);
    }

    private void add() {
        try {
        	ToDo todo;
        	if(!(title.getText().isEmpty())) {
	            todo = new ToDo(
	                    title.getText(),
	                    due.getText(),
	                    reminder.getText(),
	                    (String) classIds.getSelectedItem()
	            );
        
	            Printer.printTodo(todo);
	
	            storage.addToDo(todo, user);
	            displayer.AddToDo(todo);
	            dispose();
        	}
        	else {
        		  handleInvalid();
        	}
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void handleInvalid() {
        invalid.setText("Unable to add todo");
        repaint();
    }

    private void reset() {
        title.setText("");
        due.setText("");
        reminder.setText("");
        classIds.setSelectedIndex(0);
    }
}
