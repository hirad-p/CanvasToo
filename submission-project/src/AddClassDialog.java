import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class AddClassDialog extends JDialog {
    private Container container;
    private JLabel idL, titleL, startTimeL, endTimeL, startDateL, endDateL, recurringL, invalid;
    private JTextField  id, title, startTime, endTime, startDate, endDate;
    private String recurring = "";
    private JButton addButton, resetButton, backButton;
    private JCheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    private boolean success;

    private User user;
    private LectureClassStorage lectureClassStorage;
    private ClassesDisplayer displayer;

    public AddClassDialog(ClassesDisplayer displayer) {
        super(displayer.canvas.frame, "Add Class", true);
        this.displayer = displayer;
        user = displayer.canvas.getUser();
        lectureClassStorage = new LectureClassStorage();

        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel holder = new JPanel();
        holder.setLayout(new GridLayout(7, 2));
        
        idL = new JLabel("* Id (Ex.CS 157): ");
        id = new JTextField();
        holder.add(idL); holder.add(id);

        titleL = new JLabel("Title (Ex. Database): ");
        title = new JTextField();
        holder.add(titleL); holder.add(title);

        startTimeL = new JLabel("* Start Time (Format: hh:mm:ss): ");
        startTime = new JTextField();
        holder.add(startTimeL); holder.add(startTime);

        endTimeL = new JLabel("* End Time (Format: hh:mm:ss): ");
        endTime = new JTextField();
        holder.add(endTimeL); holder.add(endTime);

        startDateL = new JLabel("* Start Date (Format: yyyy-mm-dd): ");
        startDate = new JTextField();
        holder.add(startDateL); holder.add(startDate);

        endDateL = new JLabel("* End Date (Format: yyyy-mm-dd): ");
        endDate = new JTextField();
        holder.add(endDateL); holder.add(endDate);
        
        container.add(holder);

        // @todo - switch to check boxes
        recurringL = new JLabel("* Recurring: ");
        //recurring = new JTextField();
        container.add(recurringL); //container.add(recurring);
        
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
        container.add(holder2);
        
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
            	recurring+="Tu"; 
            	if (e.getStateChange() == ItemEvent.DESELECTED) {
            		recurring.replace("Tu", "");
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
            	recurring+="Th"; 
            	if (e.getStateChange() == ItemEvent.DESELECTED) {
            		recurring.replace("Th", "");
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
            	recurring+="Sa"; 
            	if (e.getStateChange() == ItemEvent.DESELECTED) {
            		recurring.replace("Sa", "");
                }
             }
          });
        
        sunday.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {             
            	recurring+="Su"; 
            	if (e.getStateChange() == ItemEvent.DESELECTED) {
            		recurring.replace("Su", "");
                }
             }
          });

        // buttons
        invalid = new JLabel();
        invalid.setForeground(Color.red);
        addButton = new JButton("Add");
        addButton.addActionListener(e -> add());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> reset());
        backButton = new JButton("Back");
        backButton.addActionListener(e -> back());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JPanel holder3 = new JPanel();
        holder3.setLayout(new BorderLayout());
        buttonPanel.add(addButton); buttonPanel.add(resetButton); buttonPanel.add(backButton);
        holder3.add(invalid, BorderLayout.WEST); holder3.add(buttonPanel, BorderLayout.EAST);
        container.add(holder3);

        pack();
        setResizable(false);
        setLocationRelativeTo(displayer.canvas.frame);
    }
    
    private void back() {
		// TODO Auto-generated method stub
		dispose();
	}

	public User user() {
        return user;
    }

    private void add() {
        try {
            success = !(id.getText().isEmpty() ||  
            		startTime.getText().isEmpty() || endTime.getText().isEmpty() 
            		|| startDate.getText().isEmpty() || endDate.getText().isEmpty()
            		|| recurring.length()==0);

            if (success) {
            	LectureClass c = new LectureClass(
                        id.getText(),
                        title.getText(),
                        startTime.getText(),
                        endTime.getText(),
                        startDate.getText(),
                        endDate.getText(),
                        recurring
                );
                lectureClassStorage.addClass(c, user);
                displayer.AddClass(c);
                dispose();
            }
            handleInvalid();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void handleInvalid() {
        invalid.setText("Unable to add class");
        repaint();
        success = false;
    }

    private void reset() {
        id.setText("");
        title.setText("");
        startTime.setText("");
        endTime.setText("");
        startDate.setText("");
        endDate.setText("");
        recurring = "";
    }
    
    public boolean success() {
        return this.success;
    }
}
