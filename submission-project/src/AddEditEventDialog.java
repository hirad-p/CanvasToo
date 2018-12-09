import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddEditEventDialog extends JDialog {
    private Container container;
    private JLabel titleL, startDateL, endDateL, startTimeL, endTimeL, recurringL;
    private JTextField title, startDate, endDate, startTime, endTime;
    private JComboBox<String> classIds;
    private JButton saveButton;
    private JCheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    private boolean add;
    private Event event;
    private EventStorage storage;
    private EventDisplayer displayer;
    private String recurring = "";
    private User user;

    public AddEditEventDialog(EventDisplayer displayer, ArrayList<LectureClass> classes, Event event, boolean add) {
        super(displayer.canvas.frame, "Add/Edit Event", true);
        this.displayer = displayer;
        this.event = event;
        this.add = add;
        user = displayer.canvas.getUser();
        storage = new EventStorage();

        container = getContentPane();

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        titleL = new JLabel("Title:");
        title = new JTextField(event.getTitle(), 20);
        header.add(titleL);
        header.add(title);
        String[] ids = new String[classes.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = classes.get(i).getClassID();
        }
        classIds = new JComboBox<>(ids);
        header.add(classIds);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        startDateL = new JLabel("Start Date (yyyy-mm-dd): ");
        startDate = new JTextField(event.getStartDate(), 15);
        endDateL = new JLabel("End Date (yyyy-mm-dd): ");
        endDate = new JTextField(event.getEndDate(), 15);

        datePanel.add(startDateL);
        datePanel.add(startDate);
        datePanel.add(endDateL);
        datePanel.add(endDate);

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        startTimeL = new JLabel("Start Time (hh:mm:ss): ");
        startTime = new JTextField(event.getStartTime(), 15);
        endTimeL = new JLabel("End Time (hh:mm:ss): ");
        endTime = new JTextField(event.getEndTime(), 15);

        timePanel.add(startTimeL);
        timePanel.add(startTime);
        timePanel.add(endTimeL);
        timePanel.add(endTime);

        // @todo - switch to check boxes
        recurringL = new JLabel("Recurring: ");

        JPanel holder2 = new JPanel();
        holder2.setLayout(new GridLayout(1, 2));

        holder2.add(recurringL);

        JPanel cBoxes = new JPanel();
        cBoxes.setLayout(new GridLayout(0, 1));

        monday = new JCheckBox("Monday");
        monday.setMnemonic(KeyEvent.VK_M);
        monday.setSelected(false);

        tuesday = new JCheckBox("Tuesday");
        tuesday.setMnemonic(KeyEvent.VK_T);
        tuesday.setSelected(false);

        wednesday = new JCheckBox("Wednesday");
        wednesday.setMnemonic(KeyEvent.VK_W);
        wednesday.setSelected(false);

        thursday = new JCheckBox("Thursday");
        thursday.setMnemonic(KeyEvent.VK_H);
        thursday.setSelected(false);

        friday = new JCheckBox("Friday");
        friday.setMnemonic(KeyEvent.VK_F);
        friday.setSelected(false);

        saturday = new JCheckBox("Saturday");
        saturday.setMnemonic(KeyEvent.VK_S);
        saturday.setSelected(false);

        sunday = new JCheckBox("Sunday");
        sunday.setMnemonic(KeyEvent.VK_U);
        sunday.setSelected(false);

        cBoxes.add(monday);
        cBoxes.add(tuesday);
        cBoxes.add(wednesday);
        cBoxes.add(thursday);
        cBoxes.add(friday);
        cBoxes.add(saturday);
        cBoxes.add(sunday);
        holder2.add(cBoxes);


        //listener for the check boxes.
        monday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="M";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("M", "");
                }
            }
        });

        tuesday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="TU";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("TU", "");
                }
            }
        });

        wednesday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="W";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("W", "");
                }
            }
        });

        thursday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="TH";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("TH", "");
                }
            }
        });

        friday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="F";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("F", "");
                }
            }
        });

        saturday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="SA";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("SA", "");
                }
            }
        });

        sunday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                recurring+="SU";
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    recurring.replace("SU", "");
                }
            }
        });


        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> persist());
        footer.add(saveButton);

        container.add(header);
        container.add(datePanel);
        container.add(timePanel);
        container.add(recurringL);
        container.add(holder2);
        container.add(footer);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        pack();
        setLocationRelativeTo(displayer.canvas.frame);
    }

    private void persist() {
        try {
            if (!"".equals(title.getText())) {
                event.setTitle(title.getText());
            }

            event.setStartDate(startDate.getText());
            event.setEndDate(endDate.getText());
            event.setStartTime(startTime.getText());
            event.setEndTime(endTime.getText());
            event.setRecurring(recurring);
            if (add) {
                storage.addEvent(event, user, new LectureClass((String)classIds.getSelectedItem()));
            } else {
                storage.editEvent(event, new LectureClass((String)classIds.getSelectedItem()));
            }
            dispose();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
