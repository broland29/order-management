package presentation.delete_panels;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**Generic panel for deleting entities (clients, orders, products)
 * <p>User can choose an existing entity through a dropdown</p>
 * <p>Checking if deletion is possible is not done here</p>
 */
public class DeletePanel<T extends Model> extends JPanel{

    private ArrayList<T> currentEntities;

    JPanel upperPanel;
    JPanel lowerPanel;

    JPanel entityPanel;
    JLabel entityLabel;
    JPanel entityDropdownPanel;
    JComboBox<String> entityDropdown;

    JPanel messagePanel;
    JLabel messageLabel;

    JPanel buttonPanel;
    JButton proceedButton;
    JButton backButton;


    /** Basic constructor */
    public DeletePanel(){
        this.setLayout(new GridLayout(2,1));
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(20,80,40,80,new Color(250, 237, 205)));

        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridLayout(2,1));

        entityPanel = new JPanel();
        entityPanel.setOpaque(false);
        entityPanel.setLayout(new GridBagLayout());
        entityLabel = new JLabel("Choose entity to delete:");
        entityLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        entityLabel.setForeground(new Color(66, 37, 24));
        entityPanel.add(entityLabel);

        entityDropdownPanel = new JPanel();
        entityDropdownPanel.setOpaque(false);
        entityDropdown = new JComboBox<>();
        entityDropdown.setBackground(Color.WHITE);
        entityDropdown.setFocusable(false);
        entityDropdown.setPreferredSize(new Dimension(500,45));
        entityDropdownPanel.add(entityDropdown);

        upperPanel.add(entityPanel);
        upperPanel.add(entityDropdownPanel);


        lowerPanel = new JPanel(new GridLayout(2,1));
        lowerPanel.setOpaque(false);

        messagePanel = new JPanel();
        messagePanel.setOpaque(false);
        messagePanel.setLayout(new GridBagLayout());
        messageLabel = new JLabel();
        messageLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        messagePanel.add(messageLabel);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        proceedButton = new JButton("Delete");
        proceedButton.setPreferredSize(new Dimension(150,36));
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150,36));

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(proceedButton);
        buttons.add(backButton);

        for (JButton b : buttons){
            b.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
            b.setFocusable(false);
            b.setBackground(new Color(233, 237, 201));
            b.setForeground(new Color(66, 37, 24));
            buttonPanel.add(b);
        }

        lowerPanel.add(messagePanel);
        lowerPanel.add(buttonPanel);

        this.add(upperPanel);
        this.add(lowerPanel);
    }


    /** Sets the value of currentEntities based on input parameter and initializes dropdown */
    public void setDropdowns(ArrayList<T> currentEntities){
        this.currentEntities = currentEntities;

        entityDropdown.removeAllItems();
        for (T entity : currentEntities){
            entityDropdown.addItem(entity.toString());
        }
    }


    /** Getter for proceedButton */
    public JButton getProceedButton() {return proceedButton;}
    /** Getter for backButton */
    public JButton getBackButton() {return backButton;}


    /** Adds action listeners to both buttons */
    public void addActionListener(ActionListener al){
        proceedButton.addActionListener(al);
        backButton.addActionListener(al);
    }


    /** Returns the index of selected entity (dropdown) */
    public int getSelectedIndex(){return entityDropdown.getSelectedIndex();}


    /** Returns the entity at index "index" (list) */
    public T getEntity(int index){return currentEntities.get(index);}


    /** Removes entity (list) */
    public void removeEntityFromList(int index){currentEntities.remove(index);}


    /** Removes entity (dropdown) */
    public void removeEntityFromDropdown(int index){entityDropdown.removeItemAt(index);}


    /** Sets messageLabel - error or success messages */
    public void setMessage(String message){messageLabel.setText(message);}


    /** Brings the modifiable graphic components to their initial state */
    public void reset(){
        entityDropdown.setSelectedIndex(0);
        messageLabel.setText("");
    }
}
