package presentation.edit_panels;
import business_logic.InvalidInputException;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**Panel for editing products
 * <p>User can choose an existing product through a dropdown</p>
 * <p>In order to modify data, user must (correctly) fill every data</p>
 */
public class ProductEditPanel extends JPanel{

    private ArrayList<Product> currentProducts;

    JPanel upperPanel;
    JPanel middlePanel;
    JPanel lowerPanel;

    JPanel productPanel;
    JLabel productLabel;
    JPanel productDropdownPanel;
    JComboBox<String> productDropdown;

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
    public ProductEditPanel(){
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(20,80,40,80,new Color(250, 237, 205)));

        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridLayout(2,1));

        productPanel = new JPanel();
        productPanel.setOpaque(false);
        productPanel.setLayout(new GridBagLayout());
        productLabel = new JLabel("Choose product to edit:");
        productLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        productLabel.setForeground(new Color(66, 37, 24));
        productPanel.add(productLabel);

        productDropdownPanel = new JPanel();
        productDropdownPanel.setOpaque(false);
        productDropdown = new JComboBox<>();
        productDropdown.setBackground(Color.WHITE);
        productDropdown.setFocusable(false);
        productDropdown.setPreferredSize(new Dimension(500,45));
        productDropdownPanel.add(productDropdown);

        upperPanel.add(productPanel);
        upperPanel.add(productDropdownPanel);

        middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new GridLayout(3,1));

        namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new GridBagLayout());
        nameLabel = new JLabel("New Product Name:  ");
        nameLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        nameField = new JTextField(null,17);
        nameField.setPreferredSize(new Dimension(0,36));    //0 overwritten by initialization
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        quantityPanel = new JPanel();
        quantityPanel.setOpaque(false);
        quantityPanel.setLayout(new GridBagLayout());
        quantityLabel = new JLabel("New Quantity:            ");
        quantityLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        quantityField = new JTextField(null,17);
        quantityField.setPreferredSize(new Dimension(0,36));
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);

        pricePanel = new JPanel();
        pricePanel.setOpaque(false);
        pricePanel.setLayout(new GridBagLayout());
        priceLabel = new JLabel("New Unit Price:         ");
        priceLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        priceField = new JTextField(null,17);
        priceField.setPreferredSize(new Dimension(0,36));
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);

        middlePanel.add(namePanel);
        middlePanel.add(quantityPanel);
        middlePanel.add(pricePanel);


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

        this.add(upperPanel,BorderLayout.NORTH);
        this.add(middlePanel,BorderLayout.CENTER);
        this.add(lowerPanel,BorderLayout.SOUTH);
    }


    /** Sets the value of currentProducts based on input parameter and initializes dropdown */
    public void setDropdown(ArrayList<Product> currentProducts){
        this.currentProducts = currentProducts;

        productDropdown.removeAllItems();
        for (Product product : currentProducts){
            productDropdown.addItem(product.toString());
        }
    }


    /** Returns the product name given by the user */
    public String getProductName(){
        return nameField.getText();
    }

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


    /** Returns the index of product client (dropdown) */
    public int getSelectedIndex(){return productDropdown.getSelectedIndex();}
    /** Returns the product at index "index" (list) */
    public Product getProduct(int index){
        return currentProducts.get(index);
    }


    /** Updates product (list) */
    public void updateCurrentProductInList(Product product, int index) {
        currentProducts.set(index,product);
    }

    /** Updates product (dropdown) */
    public void updateCurrentProductInDropdown(Product product, int index) {
        productDropdown.removeItemAt(index);
        productDropdown.insertItemAt(product.toString(),index);
        productDropdown.setSelectedIndex(index);
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
        productDropdown.setSelectedIndex(0);
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        messageLabel.setText("");
    }
}
