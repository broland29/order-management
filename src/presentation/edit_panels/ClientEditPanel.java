package presentation.edit_panels;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**Panel for editing clients
 * <p>User can choose an existing client through a dropdown</p>
 * <p>In order to modify data, user must (correctly) fill every data</p>
 */
public class ClientEditPanel extends JPanel{

    private ArrayList<Client> currentClients;

    JPanel upperPanel;
    JPanel middlePanel;
    JPanel lowerPanel;

    JPanel clientPanel;
    JLabel clientLabel;
    JPanel clientDropdownPanel;
    JComboBox<String> clientDropdown;

    JPanel namePanel;
    JLabel nameLabel;
    JTextField nameField;

    JPanel phonePanel;
    JLabel phoneLabel;
    JTextField phoneField;

    JPanel messagePanel;
    JLabel messageLabel;
    JPanel buttonPanel;
    JButton proceedButton;
    JButton backButton;


    /** Basic constructor */
    public ClientEditPanel(){
        this.setLayout(new GridLayout(3,1));
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(20,80,40,80,new Color(250, 237, 205)));

        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridLayout(2,1));

        clientPanel = new JPanel();
        clientPanel.setOpaque(false);
        clientPanel.setLayout(new GridBagLayout());
        clientLabel = new JLabel("Choose client to edit:");
        clientLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        clientLabel.setForeground(new Color(66, 37, 24));
        clientPanel.add(clientLabel);

        clientDropdownPanel = new JPanel();
        clientDropdownPanel.setOpaque(false);
        clientDropdown = new JComboBox<>();
        clientDropdown.setBackground(Color.WHITE);
        clientDropdown.setFocusable(false);
        clientDropdown.setPreferredSize(new Dimension(500,45));
        clientDropdownPanel.add(clientDropdown);

        upperPanel.add(clientPanel);
        upperPanel.add(clientDropdownPanel);

        middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new GridLayout(2,1));

        namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new GridBagLayout());
        nameLabel = new JLabel("New Client Name:      ");
        nameLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        nameField = new JTextField(null,17);
        nameField.setPreferredSize(new Dimension(0,36));    //0 overwritten by initialization
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        phonePanel = new JPanel();
        phonePanel.setOpaque(false);
        phonePanel.setLayout(new GridBagLayout());
        phoneLabel = new JLabel("New Phone Number:  ");
        phoneLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        phoneField = new JTextField(null,17);
        phoneField.setPreferredSize(new Dimension(0,36));
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);

        middlePanel.add(namePanel);
        middlePanel.add(phonePanel);


        lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridLayout(2,1));

        messagePanel = new JPanel();
        messagePanel.setOpaque(false);
        messagePanel.setLayout(new GridBagLayout());
        messageLabel = new JLabel();
        messageLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        messagePanel.add(messageLabel);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1,2,20,60));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(10,80,10,80,new Color(250, 237, 205)));


        proceedButton = new JButton("Edit");
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
        this.add(middlePanel);
        this.add(lowerPanel);
    }

    /** Sets the value of currentClients based on input parameter and initializes dropdown */
    public void setDropdown(ArrayList<Client> currentClients){
        this.currentClients = currentClients;

        clientDropdown.removeAllItems();
        for (Client client : currentClients){
            clientDropdown.addItem(client.toString());
        }
    }

    /** Returns the client name given by the user */
    public String getNameText(){return nameField.getText();}
    /** Returns the phone number given by the user */
    public String getPhoneText(){return phoneField.getText();}


    /** Getter for proceedButton */
    public JButton getProceedButton() {return proceedButton;}
    /** Getter for backButton */
    public JButton getBackButton() {return backButton;}


    /** Returns the index of selected client (dropdown) */
    public int getSelectedIndex(){return clientDropdown.getSelectedIndex();}


    /** Returns the client at index "index" (list) */
    public Client getClient(int index){return currentClients.get(index);}


    /** Updates client (list) */
    public void updateCurrentClientInList(Client client, int index) {currentClients.set(index,client);}


    /** Updates client (dropdown) */
    public void updateCurrentClientInDropdown(Client client, int index) {
        clientDropdown.removeItemAt(index);
        clientDropdown.insertItemAt(client.toString(),index);
        clientDropdown.setSelectedIndex(index);
    }


    /** Adds action listeners to both buttons */
    public void addActionListener(ActionListener al){
        proceedButton.addActionListener(al);
        backButton.addActionListener(al);
    }


    /** Sets messageLabel - error or success messages */
    public void setMessage(String message){messageLabel.setText(message);}


    /** Brings the modifiable graphic components to their initial state */
    public void reset(){
        clientDropdown.setSelectedIndex(0);
        nameField.setText("");
        phoneField.setText("");
        messageLabel.setText("");
    }
}
