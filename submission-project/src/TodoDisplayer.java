import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TodoDisplayer extends JDialog {
    public CanvasTooUI canvas;

    private ArrayList<ToDo> todos;
    private ArrayList<LectureClass> classes;
    private ArrayList<ToDoChange> changes;
    private User user;

    private ToDoStorage storage;
    private LectureClassStorage lectureClassStorage;

    private JTable table;

    private String[] columns = {"Title", "Reminder", "Due", "Class"};
    private String[] database = {"title", "reminder", "due", "classID"};

    public TodoDisplayer(CanvasTooUI canvas) {
        this.canvas = canvas;
        user = canvas.getUser();
        storage = new ToDoStorage();
        lectureClassStorage = new LectureClassStorage();
        changes = new ArrayList<>();

        Container container = getContentPane();
        try {
            // get the user's classes
            classes = lectureClassStorage.getClasses(user);
            todos = new ArrayList<>();
            for (LectureClass lectureClass : classes) {
                todos.addAll(storage.getTodos(user, lectureClass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTable();

        JButton done, edit, add, back;
        done = new JButton("Mark Done");
        done.addActionListener(e -> {
            System.out.println("Clicked on done");
            int[] selected = table.getSelectedRows();
            for (int i : selected) {
                try {
                    ToDo todo = todos.get(i);
                    storage.markDone(todo);
                    dispose();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        edit = new JButton("Edit");
        edit.addActionListener(e -> {
            System.out.println("Clicked on edit");
            for (ToDoChange change : changes) {
                try {
                    storage.editToDo(change);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        add = new JButton("Add");
        add.addActionListener(e -> {
            System.out.println("Clicked on add");
            dispose();
            AddTodoDialog dialog = new AddTodoDialog(this, classes);
            dialog.setVisible(true);
        });
        
        back = new JButton("Back");
        back.addActionListener(e -> {
            System.out.println("Clicked on back");
            dispose();
        });

        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 0, 10, 0));

        //GridBagConstraints gbc = new GridBagConstraints();
        //gbc.fill = GridBagConstraints.VERTICAL;
        //buttonPanel.add(done, gbc);
        //buttonPanel.add(edit, gbc);
        //buttonPanel.add(add, gbc);
        //buttonPanel.add(back, gbc);
        buttonPanel.add(done);
        buttonPanel.add(edit);
        buttonPanel.add(add);
        buttonPanel.add(back);

        container.setLayout(new BorderLayout());
        container.add(table.getTableHeader(), BorderLayout.PAGE_START);
        container.add(table, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(canvas.frame);
    }

    public void AddToDo(ToDo toDo) {
        todos.add(toDo);
        refresh();
    }

    public void refresh() {
        getContentPane().remove(table);
        createTable();
        getContentPane().add(table, BorderLayout.CENTER);
        repaint();
    }

    private void createTable() {
        table = new JTable(prepareData(), columns);
        table.setFillsViewportHeight(true);
        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                String value = String.valueOf(table.getValueAt(row, col));
                ToDo todo = todos.get(row);
                String old = todo.get(database[col]);

                if (!old.equals(value)) {
                    changes.add(new ToDoChange(todo.getId(), database[col], value));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
    }

    private Object[][] prepareData() {
        Object[][] data = new Object[todos.size()][columns.length];

        ToDo todo;
        for (int i = 0; i < todos.size(); i++) {
            todo = todos.get(i);
            Printer.printTodo(todo);
            // must match column order
            Object[] id = {
                    todo.getTitle(),
                    todo.getReminder(),
                    todo.getDue(),
                    todo.getClassId()
            };
            data[i] = id;
        }

        return data;
    }
}
