package gui.diplayer;

import gui.CanvasTooUI;
import gui.diplayer.dialog.AddClassDialog;
import gui.diplayer.util.LectureClassChange;
import model.LectureClass;
import model.User;
import storage.LectureClassStorage;
import storage.UserStorage;
import utils.Printer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ClassesDisplayer extends JDialog {
    public CanvasTooUI canvas;
    private ArrayList<LectureClass> classes;
    private User user;

    private LectureClassStorage lectureClassStorage;
    private UserStorage userStorage;
    private JTable table;

    // @todo - come up with a framework for this
    private String[] columnNames = {"Id", "Title", "Start Time", "End Time", "Start Date", "End Date", "Recurring"};
    private String[] databaseName = {"id", "title", "startTime", "endTime", "startDate", "endDate", "recurring"};

    private ArrayList<LectureClassChange> edits;

    public ClassesDisplayer(CanvasTooUI canvas) {
        this.canvas = canvas;
        user = canvas.getUser();
        lectureClassStorage = new LectureClassStorage();
        edits = new ArrayList<>();

        Container container = getContentPane();
        try {
            // get the user's classes
            classes = lectureClassStorage.getClasses(user);
            System.out.println("Constructor" + user.getId());
            Printer.printClasses(classes);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userStorage = new UserStorage();

        createTable();

        JButton delete, edit, add;
        delete = new JButton("Delete");
        delete.setOpaque(true);
        delete.addActionListener(e -> {
            System.out.println("Clicked on delete");
            int[] selected = table.getSelectedRows();
                for (int i : selected) {
                    try {
                        LectureClass c = classes.get(i);
                        lectureClassStorage.deleteClass(c.getClassID(), user.getId());
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }

            }
        });

        edit = new JButton("Edit");
        edit.addActionListener(e -> {
            System.out.println("Clicked on edit");
            for (LectureClassChange change : edits) {
                try {
                    lectureClassStorage.editClass(change);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        add = new JButton("Add");
        add.addActionListener(e -> {
            System.out.println("Clicked on add");
            AddClassDialog addDialog = new AddClassDialog(this);
            addDialog.setVisible(true);
        });

        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        buttonPanel.add(delete, gbc);
        buttonPanel.add(edit, gbc);
        buttonPanel.add(add, gbc);

        container.setLayout(new BorderLayout());
        container.add(table.getTableHeader(), BorderLayout.PAGE_START);
        container.add(table, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(canvas.frame);
    }

    public void AddClass(LectureClass c) {
        classes.add(c);
        getContentPane().remove(table);
        createTable();
        getContentPane().add(table, BorderLayout.CENTER);
        repaint();
    }

    private void createTable() {
        table = new JTable(prepareData(), columnNames);
        table.setFillsViewportHeight(true);
        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                String value = String.valueOf(table.getValueAt(row, col));
                LectureClass lectureClass = classes.get(row);
                String old = lectureClass.get(databaseName[col]);

                if (!old.equals(value)) {
                    edits.add(new LectureClassChange(lectureClass.getClassID(), user.getId(), databaseName[col], value));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
    }

    private Object[][] prepareData() {
        Printer.printClasses(classes);
        Object[][] data = new Object[classes.size()][columnNames.length];

        LectureClass lectureClass;
        for (int i = 0; i < classes.size(); i++) {
            lectureClass = classes.get(i);
            // must match column order
            Object[] id = {
                    lectureClass.getClassID(),
                    lectureClass.getTitle(),
                    lectureClass.getStartTime(),
                    lectureClass.getEndTime(),
                    lectureClass.getStartDate(),
                    lectureClass.getEndDate(),
                    lectureClass.getRecurring()
            };
            data[i] = id;
        }

        return data;
    }
}
