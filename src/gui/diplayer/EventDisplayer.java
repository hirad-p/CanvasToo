package gui.diplayer;

import gui.CanvasTooUI;
import gui.diplayer.dialog.AddEditEventDialog;
import gui.diplayer.dialog.AddEditNoteDialog;
import model.Event;
import model.LectureClass;
import model.Note;
import model.Count;
import model.User;
import storage.EventStorage;
import storage.LectureClassStorage;

import java.awt.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EventDisplayer extends JDialog {
    public CanvasTooUI canvas;
    private User user;

    private LectureClassStorage lectureClassStorage;
    private EventStorage storage;

    JTabbedPane tabbedPane;
    private HashMap<String, JList> noteMap;
    private ArrayList<LectureClass> classes;
    private ArrayList<Event> events;

    public EventDisplayer(CanvasTooUI canvas) {
        super(canvas.frame, "Events ", true);

        this.canvas = canvas;
        user = canvas.getUser();
        storage = new EventStorage();
        lectureClassStorage = new LectureClassStorage();
        noteMap = new HashMap<>();
        Container container = getContentPane();

        try {
            ArrayList<Count> counts = storage.getEventsPerClass(user);
            classes = lectureClassStorage.getClasses(user);
            tabbedPane = new JTabbedPane();
            for (LectureClass lectureClass : classes) {
                int count = 0;
                for (Count eventCount : counts) {
                    if (eventCount.id.equals(lectureClass.getClassID())) {
                        count = eventCount.count;
                        break;
                    }
                }
                String title = lectureClass.getClassID() + " [" + count + "]";
                tabbedPane.addTab(title, createClassPane(lectureClass));
            }

            JPanel buttons = createButtonPanel();

            container.add(tabbedPane);
            container.add(buttons, BorderLayout.PAGE_END);
            pack();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel createClassPane(LectureClass lectureClass) {
        ArrayList<Note> notes;
        try {
            events = storage.getEventsForClass(user, lectureClass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        JList list = new JList(events.toArray());
        list.setCellRenderer(new EventRenderer());
        JScrollPane pane = new JScrollPane(list);

        JPanel panel = new JPanel();
        panel.add(list);

        noteMap.put(lectureClass.getClassID(), list);
        return panel;
    }

    private JPanel createButtonPanel() {
        JButton delete, edit, add, back;
        delete = new JButton("Delete");
        delete.addActionListener(e -> {
            System.out.println("Clicked on Delete");
            Event event = getSelectedEvent();
            System.out.println("Deleting note " + event.getId());
            try {
                storage.deleteEvent(event);
                dispose();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        edit = new JButton("Edit");
        edit.addActionListener(e -> {
            System.out.println("Clicked on Edit");
            Event event = getSelectedEvent();
            AddEditEventDialog addEditEventDialog = new AddEditEventDialog(this, classes, event, false);
            addEditEventDialog.setVisible(true);
        });

        add = new JButton("Add");
        add.addActionListener(e -> {
            System.out.println("Clicked on Add");
            Event event = new Event("", "", "", "", "", "", "", "");
            AddEditEventDialog addEditEventDialog = new AddEditEventDialog(this, classes, event, true);
            addEditEventDialog.setVisible(true);
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
        //buttonPanel.add(delete, gbc);
        //buttonPanel.add(edit, gbc);
        //buttonPanel.add(add, gbc);
        //buttonPanel.add(add, gbc); 
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        buttonPanel.add(add);
        buttonPanel.add(back);

        return buttonPanel;
    }

    private Event getSelectedEvent() {
        int index = tabbedPane.getSelectedIndex();
        LectureClass lectureClass = classes.get(index);
        JList list = noteMap.get(lectureClass.getClassID());
        return (Event) list.getSelectedValue();
    }
}

class EventRenderer extends JLabel implements ListCellRenderer {
    public EventRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        Event entry = (Event) value;
        setText(entry.getTitle() + " at " + entry.getStartTime() + " on " + entry.getStartDate() + " for " + entry.getClassID());
        if (isSelected) {
            setBackground(Color.GRAY);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}