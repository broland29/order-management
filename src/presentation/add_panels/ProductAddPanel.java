package presentation.add_panels;

import business_logic.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**Panel for adding new products
 * <p>In order to add a new product, user must (correctly) fill every data</p>
 */
public class ProductAddPanel extends JPanel{

    JPanel upperPanel;
    JPanel lowerPanel;

    JPanel namePanel;
    JLabel nameLabel;
    JTextField nameField;

    JPanel quantityPanel;
    JLabel quantityLabel;
    JTextField quantityField;

    JPanel pricePanel;
    JLabel priceLabel;
    JTextField priceField;

    JPanel messagePanel;
    JLabel messageLabel;

    JPanel buttonPanel;
    JButton proceedButton;
    JButton backButton;


    /** Basic constructor */
    public ProductAddPanel(){
        this.setLayout(new GridLayout(2,1));
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(80,80,80,80,new Color(250, 237, 205)));

        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridLayout(3,1));

        namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new GridBagLayout());
        nameLabel = new JLabel("Product Name:  ");
        nameLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        nameField = new JTextField(null,17);
        nameField.setPreferredSize(new Dimension(0,36));    //0 overwritten by initialization
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        quantityPanel = new JPanel();
        quantityPanel.setOpaque(false);
        quantityPanel.setLayout(new GridBagLayout());
        quantityLabel = new JLabel("Quantity:            ");
        quantityLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        quantityField = new JTextField(null,17);
        quantityField.setPreferredSize(new Dimension(0,36));
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);

        pricePanel = new JPanel();
        pricePanel.setOpaque(false);
        pricePanel.setLayout(new GridBagLayout());
        priceLabel = new JLabel("Unit Price:         ");
        priceLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        priceField = new JTextField(null,17);
        priceField.setPreferredSize(new Dimension(0,36));
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);

        upperPanel.add(namePanel);
        upperPanel.add(quantityPanel);
        upperPanel.add(pricePanel);


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

        proceedButton = new JButton("Add Product");
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


    /** Returns the product name given by the user */
    public String getProductName(){return nameField.getText();}

    /** Returns the quantity given by the user
     * @throws InvalidInputException if parsing to int fails
     **/
    public int getProductQuantity() throws InvalidInputException {
        try{
            return Integer.parseInt(quantityField.getText());
        }catch (NumberFormatException nfe){
            throw new InvalidInputException("Invalid QUANTITY!");
        }
    }

    /** Returns the price given by the user
     * @throws InvalidInputException if parsing to float fails
     **/
    public float getProductPrice() throws InvalidInputException {
        try{
            return Float.parseFloat(priceField.getText());
        }catch (NumberFormatException nfe){
            throw new InvalidInputException("Invalid PRICE!");
        }
    }


    /** Getter for proceedButton */
    public JButton getProceedButton() {
        return proceedButton;
    }
    /** Getter for backButton */
    public JButton getBackButton() {
        return backButton;
    }


    /** Adds action listeners to both buttons */
    public void addActionListener(ActionListener al){
        proceedButton.addActionListener(al);
        backButton.addActionListener(al);
    }


    /** Sets messageLabel - error or success messages */
    public void setMessage(String message){
        messageLabel.setText(message);
    }


    /** Brings the modifiable graphic components to their initial state */
    public void reset(){
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        messageLabel.setText("");
    }
}
