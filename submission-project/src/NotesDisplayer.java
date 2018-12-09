import java.awt.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class NotesDisplayer extends JDialog {
    public CanvasTooUI canvas;
    private User user;

    private LectureClassStorage lectureClassStorage;
    private NotesStorage storage;

    JTabbedPane tabbedPane;
    private HashMap<String, JList> noteMap;
    private ArrayList<LectureClass> classes;

    public NotesDisplayer(CanvasTooUI canvas) {
        super(canvas.frame, "Notes", true);

        this.canvas = canvas;
        user = canvas.getUser();
        storage = new NotesStorage();
        lectureClassStorage = new LectureClassStorage();
        noteMap = new HashMap<>();
        Container container = getContentPane();

        try {
            ArrayList<Count> counts = storage.getNotesPerClass(user);
            classes = lectureClassStorage.getClasses(user);
            tabbedPane = new JTabbedPane();
            for (LectureClass lectureClass : classes) {
                int count = 0;
                for (Count noteCount : counts) {
                    if (noteCount.id.equals(lectureClass.getClassID())) {
                        count = noteCount.count;
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
            notes = storage.getNotesForClass(user, lectureClass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        JList list = new JList(notes.toArray());
        list.setCellRenderer(new NoteRenderer());
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
            Note note = getSelectedNote();
            System.out.println("Deleting note " + note.getId());
            try {
                storage.deleteNote(note);
                dispose();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        edit = new JButton("Edit");
        edit.addActionListener(e -> {
            System.out.println("Clicked on Edit");
            Note note = getSelectedNote();
            dispose();
            AddEditNoteDialog addEditNoteDialog = new AddEditNoteDialog(this, classes, note, false);
            addEditNoteDialog.setVisible(true);
        });

        add = new JButton("Add");
        add.addActionListener(e -> {
            System.out.println("Clicked on Add");
            Note note = new Note("", "");
            dispose();
            AddEditNoteDialog addEditNoteDialog = new AddEditNoteDialog(this, classes, note, true);
            addEditNoteDialog.setVisible(true);
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
        buttonPanel.add(delete);
        buttonPanel.add(edit);
        buttonPanel.add(add);
        buttonPanel.add(back);
        
        return buttonPanel;
    }

    private Note getSelectedNote() {
        int index = tabbedPane.getSelectedIndex();
        LectureClass lectureClass =  classes.get(index);
        JList list = noteMap.get(lectureClass.getClassID());
        return (Note) list.getSelectedValue();
    }
}

class NoteRenderer extends JLabel implements ListCellRenderer {
    public NoteRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        Note entry = (Note) value;
        setText(entry.getTitle());
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