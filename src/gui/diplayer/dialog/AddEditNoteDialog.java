package gui.diplayer.dialog;

import gui.diplayer.NotesDisplayer;
import model.LectureClass;
import model.Note;
import model.User;
import storage.NotesStorage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddEditNoteDialog extends JDialog {
    private Container container;
    private JLabel titleL, modifiedL;
    private JTextField title;
    private JTextArea body;
    private JComboBox<String> classIds;
    private JButton saveButton;

    private Note note;
    private User user;
    private NotesStorage storage;
    private NotesDisplayer displayer;

    private boolean add;

    public AddEditNoteDialog(NotesDisplayer displayer, ArrayList<LectureClass> classes, Note note, boolean add) {
        super(displayer.canvas.frame, "Add Note", true);
        this.displayer = displayer;
        this.note = note;
        this.add = add;
        user = displayer.canvas.getUser();
        storage = new NotesStorage();

        container = getContentPane();

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        titleL = new JLabel("Title:");
        title = new JTextField(note.getTitle(), 20);
        header.add(titleL);
        header.add(title);
        String[] ids = new String[classes.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = classes.get(i).getClassID();
        }
        classIds = new JComboBox<>(ids);
        header.add(classIds);

        modifiedL = new JLabel("Modified: " + note.getLastModified().toString());
        body = new JTextArea(note.getNote(), 10, 40);

        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> persist());
        footer.add(saveButton);

        container.add(header);
        container.add(modifiedL);
        container.add(body);
        container.add(footer);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        pack();
        setLocationRelativeTo(displayer.canvas.frame);
    }

    private void persist() {
        try {
            if (!"".equals(title.getText())) {
                note.setTitle(title.getText());
            }
            note.setNote(body.getText());
            if (add) {
                storage.addNote(note, user, new LectureClass((String)classIds.getSelectedItem()));
            } else {
                storage.editNote(note, new LectureClass((String)classIds.getSelectedItem()));
            }
            dispose();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
