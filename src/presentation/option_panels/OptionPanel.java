package presentation.option_panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Generic panel for choosing operation on clients/ products/ orders
 * <p>Displays the four possibilities of the user (with the chosen entities):</p>
 * <ul>
 * <li>Add</li>
 * <li>Edit</li>
 * <li>Delete</li>
 * <li>View All</li>
 * </ul>
 */
public class OptionPanel extends JPanel{

    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton viewAllButton;
    private final JButton backButton;


    /** Basic constructor */
    public OptionPanel(String option){
        this.setLayout(new GridLayout(5,1,0,10));
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(50,150,50,150,new Color(250, 237, 205)));

        addButton = new JButton("Add " + option);
        editButton = new JButton("Edit " + option);
        deleteButton = new JButton("Delete " + option);
        viewAllButton = new JButton("View " + option + "s");
        backButton = new JButton("Back");

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(viewAllButton);
        buttons.add(backButton);

        for (JButton b : buttons){
            b.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
            b.setFocusable(false);
            b.setBackground(new Color(233, 237, 201));
            b.setForeground(new Color(66, 37, 24));
            this.add(b);
        }
    }

    /** Adds action listener to each of the five buttons */
    public void addActionListener(ActionListener al){
        addButton.addActionListener(al);
        backButton.addActionListener(al);
        deleteButton.addActionListener(al);
        editButton.addActionListener(al);
        viewAllButton.addActionListener(al);
    }

    /** Getter for addButton */
    public JButton getAddButton() {
        return addButton;
    }

    /** Getter for editButton */
    public JButton getEditButton() {
        return editButton;
    }

    /** Getter for deleteButton */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /** Getter for viewAllButton */
    public JButton getViewAllButton() {
        return viewAllButton;
    }

    /** Getter for backButton */
    public JButton getBackButton() {
        return backButton;
    }
}
