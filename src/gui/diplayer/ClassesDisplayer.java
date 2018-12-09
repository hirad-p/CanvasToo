package gui.diplayer;

import gui.CanvasTooUI;
import gui.diplayer.dialog.AddClassDialog;
import gui.diplayer.util.LectureClassChange;
import model.Count;
import model.LectureClass;
import model.User;
import storage.LectureClassStorage;
import storage.UserStorage;
import utils.Printer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ClassesDisplayer extends JDialog {
    public CanvasTooUI canvas;
    private ArrayList<LectureClass> classes;
    private User user;

    private LectureClassStorage lectureClassStorage;
    private UserStorage userStorage;
    private JTable table;
    
    AddClassDialog addDialog;

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

        JLabel invalid = new JLabel("");
        invalid.setForeground(Color.red);
        JButton delete, edit, add, back;
        delete = new JButton("Delete");
        delete.setOpaque(true);
        delete.addActionListener(e -> {
            System.out.println("Clicked on delete");
            int[] selected = table.getSelectedRows();
                for (int i : selected) {
                    try {
                        LectureClass c = classes.get(i);
                        classes.remove(i);
                        lectureClassStorage.deleteClass(c.getClassID(), user.getId());
                        dispose();
                    } catch (SQLException exception) {
                        invalid.setText(exception.getMessage());
                        container.repaint();
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
                    invalid.setText(exception.getMessage());
                    container.repaint();
                    exception.printStackTrace();
                }
            }
        });

        add = new JButton("Add");
        add.addActionListener(e -> {
            System.out.println("Clicked on add");
            dispose();
            addDialog = new AddClassDialog(this);
            addDialog.setVisible(true);
        });
        
        back = new JButton("Back");
        back.addActionListener(e -> {
            System.out.println("Clicked on back");
            dispose();
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));

        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        buttonPanel.add(delete, gbc);
        buttonPanel.add(edit, gbc);
        buttonPanel.add(add, gbc);
        buttonPanel.add(back, gbc);

        infoPanel.add(invalid);
        infoPanel.add(buttonPanel);

        container.setLayout(new BorderLayout());
        container.add(table.getTableHeader(), BorderLayout.PAGE_START);
        container.add(table, BorderLayout.CENTER);
        container.add(infoPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(canvas.frame);
    }

    public void AddClass(LectureClass c) {
        classes.add(c);
        getContentPane().remove(table);
        createTable();
        getContentPane().add(table);
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
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
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
    
    private void checkRequirement20() {
    	Scanner sc = new Scanner(System.in); 
    	System.out.println("get students in the same two classes");
    	
    	System.out.println("Enter class 1: ");
    	String class1 = sc.nextLine(); //CS157A
    	
    	System.out.println("Enter class 2: ");
    	String class2 = sc.nextLine(); //CS158B
    	
    	ArrayList<User> list = new ArrayList<>();
    	
    	try {
			list = lectureClassStorage.getUsersFromClasses(class1, class2);
			
			for (int j = 0; j < list.size(); j++)
	        {
	            System.out.println(list.get(j).getFirstName() + " " + list.get(j).getLastName());
	        }
			
			if(list.size() == 0) {
				System.out.println("there are no same users in these two classes");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	sc.close();
    
    }
}
