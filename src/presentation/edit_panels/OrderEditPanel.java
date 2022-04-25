package presentation.edit_panels;
import business_logic.InvalidInputException;
import business_logic.OrderBL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**Panel for editing orders
 * <p>User can choose an existing order through a dropdown</p>
 * <p>In order to modify data, user must (correctly) fill every data</p>
 */
public class OrderEditPanel extends JPanel{

    private ArrayList<Client> currentClients;
    private ArrayList<Order> currentOrders;
    private ArrayList<Product> currentProducts;

    JPanel upperPanel;
    JPanel middlePanel;
    JPanel lowerPanel;

    JPanel orderPanel;
    JLabel orderLabel;
    JPanel orderDropdownPanel;
    JComboBox<String> orderDropdown;

    JPanel clientPanel;
    JLabel clientLabel;
    JComboBox<String> clientDropdown;

    JPanel productPanel;
    JLabel productLabel;
    JComboBox<String> productDropdown;

    JPanel quantityPanel;
    JLabel quantityLabel;
    JTextField quantityField;

    JPanel pickupPanel;
    JLabel pickupLabel;
    JPanel dropdownPanel;
    JPanel middleUpPanel;
    JPanel middleDownPanel;
    JComboBox<String> yearDropdown;
    JComboBox<String> monthDropdown;
    JComboBox<String> dayDropdown;

    JPanel messagePanel;
    JLabel messageLabel;
    JPanel buttonPanel;
    JButton proceedButton;
    JButton backButton;


    /** Basic constructor */
    public OrderEditPanel(){
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(20,80,40,80,new Color(250, 237, 205)));

        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridLayout(2,1));

        orderPanel = new JPanel();
        orderPanel.setOpaque(false);
        orderPanel.setLayout(new GridBagLayout());
        orderLabel = new JLabel("Choose order to edit:");
        orderLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        orderLabel.setForeground(new Color(66, 37, 24));
        orderPanel.add(orderLabel);

        orderDropdownPanel = new JPanel();
        orderDropdownPanel.setOpaque(false);
        orderDropdown = new JComboBox<>();
        orderDropdown.setBackground(Color.WHITE);
        orderDropdown.setFocusable(false);
        orderDropdown.setPreferredSize(new Dimension(500,45));
        orderDropdownPanel.add(orderDropdown);

        upperPanel.add(orderPanel);
        upperPanel.add(orderDropdownPanel);

        middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new GridLayout(2,1));

        middleUpPanel = new JPanel();
        middleUpPanel.setOpaque(false);
        middleUpPanel.setLayout(new GridLayout(1,2));

        clientPanel = new JPanel();
        clientPanel.setOpaque(false);
        clientPanel.setLayout(new GridLayout(2,1));
        clientLabel = new JLabel("New Client Name:");
        clientLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        clientLabel.setForeground(new Color(66, 37, 24));
        clientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] test = {"test"};
        clientDropdown = new JComboBox<>(test);
        clientDropdown.setBackground(Color.WHITE);
        clientDropdown.setFocusable(false);
        clientPanel.add(clientLabel);
        clientPanel.add(clientDropdown);
        clientPanel.setBorder(BorderFactory.createMatteBorder(5,20,5,10,new Color(250, 237, 205)));
        middleUpPanel.add(clientPanel);

        productPanel = new JPanel();
        productPanel.setOpaque(false);
        productPanel.setLayout(new GridLayout(2,1));
        productLabel = new JLabel("New Product Name:");
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        productLabel.setForeground(new Color(66, 37, 24));
        String[] productOptions = {"ProductOne","ProductTwo","ProductThree"};
        productDropdown = new JComboBox<>(productOptions);
        productDropdown.setBackground(Color.WHITE);
        productDropdown.setFocusable(false);
        productPanel.add(productLabel);
        productPanel.add(productDropdown);
        productPanel.setBorder(BorderFactory.createMatteBorder(5,10,5,20,new Color(250, 237, 205)));
        middleUpPanel.add(productPanel);

        middleDownPanel = new JPanel();
        middleDownPanel.setOpaque(false);
        middleDownPanel.setLayout(new GridLayout(1,2));

        quantityPanel = new JPanel();
        quantityPanel.setOpaque(false);
        quantityPanel.setLayout(new GridLayout(2,1));
        quantityLabel = new JLabel("New Quantity:");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        quantityLabel.setForeground(new Color(66, 37, 24));
        quantityField = new JTextField(10);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);
        quantityPanel.setBorder(BorderFactory.createMatteBorder(5,20,5,10,new Color(250, 237, 205)));
        middleDownPanel.add(quantityPanel);

        pickupPanel = new JPanel();
        pickupPanel.setOpaque(false);
        pickupPanel.setLayout(new GridLayout(2,1));

        dropdownPanel = new JPanel();
        dropdownPanel.setOpaque(false);
        dropdownPanel.setLayout(new GridLayout(1,3));

        pickupLabel = new JLabel("New Pick Up By:");
        pickupLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        pickupLabel.setForeground(new Color(66, 37, 24));
        pickupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pickupPanel.setBorder(BorderFactory.createMatteBorder(5,10,5,20,new Color(250, 237, 205)));
        dayDropdown = new JComboBox<>(OrderBL.DAY_OPTIONS);
        monthDropdown = new JComboBox<>(OrderBL.MONTH_OPTIONS);
        yearDropdown = new JComboBox<>(OrderBL.YEAR_OPTIONS);
        dayDropdown.setBackground(Color.WHITE);
        monthDropdown.setBackground(Color.WHITE);
        yearDropdown.setBackground(Color.WHITE);
        dayDropdown.setFocusable(false);
        monthDropdown.setFocusable(false);
        yearDropdown.setFocusable(false);

        dropdownPanel.add(yearDropdown);
        dropdownPanel.add(monthDropdown);
        dropdownPanel.add(dayDropdown);

        pickupPanel.add(pickupLabel);
        pickupPanel.add(dropdownPanel);

        middleDownPanel.add(pickupPanel);

        middlePanel.add(middleUpPanel);
        middlePanel.add(middleDownPanel);


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


    /** Sets the value of currentClients, currentOrders, currentProducts based on input parameters and initializes dropdowns */
    public void setDropdowns(ArrayList<Client> currentClients, ArrayList<Order> currentOrders, ArrayList<Product> currentProducts){
        this.currentClients = currentClients;
        this.currentOrders = currentOrders;
        this.currentProducts = currentProducts;

        clientDropdown.removeAllItems();
        for (Client client : currentClients)
            clientDropdown.addItem(client.toString());

        orderDropdown.removeAllItems();
        for (Order order : currentOrders)
            orderDropdown.addItem(order.toString());

        productDropdown.removeAllItems();
        for (Product product : currentProducts)
            productDropdown.addItem(product.toString());
    }


    /** Returns the quantity given by the user
     * @throws InvalidInputException if parsing to int fails
     **/
    public int getQuantity() throws InvalidInputException {
        try{
            return Integer.parseInt(quantityField.getText());
        }catch (NumberFormatException nfe){
            throw new InvalidInputException("Invalid quantity.");
        }
    }

    /** Returns the quantity given by the user
     * @throws RuntimeException if converting to java.sql.Date fails (should never occur)
     **/
    public java.sql.Date getDate(){
        try {
            String year = (String) yearDropdown.getSelectedItem();
            String month = (String) monthDropdown.getSelectedItem();
            String day = (String) dayDropdown.getSelectedItem();

            return java.sql.Date.valueOf(OrderBL.convertToJDBCDateFormat(year,month,day));
        }catch (IllegalArgumentException | NullPointerException e){
            throw new RuntimeException("Unexpected error in getDate (OrderEditPanel)");
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


    /** Returns the index of selected order (dropdown) */
    public int getSelectedIndexOrder(){return orderDropdown.getSelectedIndex();}
    /** Returns the index of selected client (dropdown) */
    public int getSelectedIndexClient(){return clientDropdown.getSelectedIndex();}
    /** Returns the index of selected product (dropdown) */
    public int getSelectedIndexProduct(){return productDropdown.getSelectedIndex();}


    /** Returns the order at index "index" (list) */
    public Order getOrder(int index){return currentOrders.get(index);}
    /** Returns the client at index "index" (list) */
    public Client getClient(int index){
        return currentClients.get(index);
    }
    /** Returns the product at index "index" (list) */
    public Product getProduct(int index){return currentProducts.get(index);}


    /** Updates order (list) */
    public void updateCurrentOrderInList(Order order, int index){currentOrders.set(index,order);}

    /** Updates order (dropdown) */
    public void updateCurrentOrderInDropdown(Order order, int index) {
        orderDropdown.removeItemAt(index);
        orderDropdown.insertItemAt(order.toString(),index);
        orderDropdown.setSelectedIndex(index);
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
        orderDropdown.setSelectedIndex(0);
        clientDropdown.setSelectedIndex(0);
        productDropdown.setSelectedIndex(0);
        quantityField.setText("");
        yearDropdown.setSelectedIndex(0);
        monthDropdown.setSelectedIndex(0);
        dayDropdown.setSelectedIndex(0);
        messageLabel.setText("");
    }
}
