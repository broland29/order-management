package presentation.add_panels;

import business_logic.InvalidInputException;
import business_logic.OrderBL;
import model.Client;
import model.Product;
import model.TemporaryOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**Panel for adding new orders
 * <p>In order to add a new order, user must (correctly) fill every data</p>
 * <p>Unlike deleting and editing, insertion works locally for multiple "sub-orders" called TemporaryOrders</p>
 * <p>When billing happens, every affected data from the database will be updated</p>
 */
public class OrderAddPanel extends JPanel{

    private ArrayList<Client> currentClients;
    private ArrayList<Product> currentProducts;
    private final ArrayList<TemporaryOrder> temporaryOrders;

    JPanel upperPanel;
    JPanel middlePanel;
    JPanel lowerPanel;

    //upperPanel related
    JPanel upperUpPanel;
    JPanel upperDownPanel;

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
    JComboBox<String> yearDropdown;
    JComboBox<String> monthDropdown;
    JComboBox<String> dayDropdown;

    //middlePanel related
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;

    //lowerPanel related
    JPanel messagePanel;
    JLabel messageLabel;

    JPanel buttonPanel;
    private final JButton addButton;
    private final JButton removeButton;
    private final JButton billButton;
    private final JButton backButton;


    /** Basic constructor */
    public OrderAddPanel(){
        temporaryOrders = new ArrayList<>();

        this.setLayout(new GridLayout(3,1));
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(30,50,30,50,new Color(250, 237, 205)));

        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridLayout(2,1));

        upperUpPanel = new JPanel();
        upperUpPanel.setOpaque(false);
        upperUpPanel.setLayout(new GridLayout(1,2));

        clientPanel = new JPanel();
        clientPanel.setOpaque(false);
        clientPanel.setLayout(new GridLayout(2,1));
        clientLabel = new JLabel("Client Name:");
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
        upperUpPanel.add(clientPanel);

        productPanel = new JPanel();
        productPanel.setOpaque(false);
        productPanel.setLayout(new GridLayout(2,1));
        productLabel = new JLabel("Product Name:");
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
        upperUpPanel.add(productPanel);

        upperDownPanel = new JPanel();
        upperDownPanel.setOpaque(false);
        upperDownPanel.setLayout(new GridLayout(1,2));

        quantityPanel = new JPanel();
        quantityPanel.setOpaque(false);
        quantityPanel.setLayout(new GridLayout(2,1));
        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        quantityLabel.setForeground(new Color(66, 37, 24));
        quantityField = new JTextField(10);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);
        quantityPanel.setBorder(BorderFactory.createMatteBorder(5,20,5,10,new Color(250, 237, 205)));
        upperDownPanel.add(quantityPanel);

        pickupPanel = new JPanel();
        pickupPanel.setOpaque(false);
        pickupPanel.setLayout(new GridLayout(2,1));

        dropdownPanel = new JPanel();
        dropdownPanel.setOpaque(false);
        dropdownPanel.setLayout(new GridLayout(1,3));

        pickupLabel = new JLabel("Pick Up By:");
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

        upperDownPanel.add(pickupPanel);

        upperPanel.add(upperUpPanel);
        upperPanel.add(upperDownPanel);


        middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 80));
        table.setFillsViewportHeight(true);

        Object[][] data = new Object[0][0];
        String[] columnNames = new String[]{"Client Name", "Product Name", "Quantity","Pick Up By"};

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);
        table.setBackground(new Color(250, 237, 205));
        table.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));

        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Color.WHITE);

        scrollPane = new JScrollPane(table);
        //scrollPane.setBorder(BorderFactory.createMatteBorder(0,0,5,0,new Color(250, 237, 205)));
        middlePanel.add(scrollPane);


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
        buttonPanel.setLayout(new GridLayout(1,4,20,60));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(10,20,10,20,new Color(250, 237, 205)));

        addButton = new JButton("Add");
        billButton = new JButton("Bill");
        backButton = new JButton("Back");
        removeButton = new JButton("Remove");

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(addButton);
        buttons.add(removeButton);
        buttons.add(billButton);
        buttons.add(backButton);

        for (JButton b : buttons){
            b.setPreferredSize(new Dimension(110,36));
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


    /** Sets the value of currentClients, and currentProducts based on input parameters and initializes dropdowns */
    public void setDropdowns(ArrayList<Client> currentClients, ArrayList<Product> currentProducts){
        this.currentClients = currentClients;
        this.currentProducts = currentProducts;

        clientDropdown.removeAllItems();
        for (Client client : currentClients){
            clientDropdown.addItem(client.toString());
        }

        productDropdown.removeAllItems();
        for (Product product : currentProducts){
            productDropdown.addItem(product.toString());
        }
    }


    /** Returns the quantity given by the user
     * @throws InvalidInputException if parsing to int fails
     **/
    public int getQuantity() throws InvalidInputException {
        try{
            return Integer.parseInt(quantityField.getText());
        }catch (NumberFormatException nfe){
            throw new InvalidInputException("Invalid QUANTITY!");
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
            throw new RuntimeException("Unexpected error in getDate (OrderAddPanel)");
        }
    }


    /** Getter for addButton */
    public JButton getAddButton() {
        return addButton;
    }
    /** Getter for removeButton */
    public JButton getRemoveButton() {
        return removeButton;
    }
    /** Getter for billButton */
    public JButton getBillButton() {
        return billButton;
    }
    /** Getter for backButton */
    public JButton getBackButton() {
        return backButton;
    }


    /** Returns the index of selected temporary order (table) */
    public int getSelectedIndexTemporaryOrder(){return table.getSelectedRow();}
    /** Returns the index of selected client (dropdown) */
    public int getSelectedIndexClient(){return clientDropdown.getSelectedIndex();}
    /** Returns the index of selected product (dropdown) */
    public int getSelectedIndexProduct(){return productDropdown.getSelectedIndex();}


    /** Returns the temporary order at index "index" (list) */
    public TemporaryOrder getTemporaryOrder(int index){return temporaryOrders.get(index);}
    /** Returns the client at index "index" (list) */
    public Client getClient(int index){return currentClients.get(index);}
    /** Returns the product at index "index" (list) */
    public Product getProduct(int index){return currentProducts.get(index);}


    /** Adds the temporary order given as parameter to the list */
    public void addTemporaryOrderToList(TemporaryOrder temporaryOrder){temporaryOrders.add(temporaryOrder);}
    /** Adds the temporary order given as parameter to the table */
    public void addTemporaryRowToTable(TemporaryOrder temporaryOrder){
        tableModel.insertRow(tableModel.getRowCount(),new Object[]{
                temporaryOrder.client().getName(),
                temporaryOrder.product().getName(),
                temporaryOrder.quantity(),temporaryOrder.pickupBy()
        });
    }


    /** Removes the temporary order at index "index" from the list */
    public void removeTemporaryOrderFromList(int index){temporaryOrders.remove(index);}
    /** Removes the temporary order at index "index" from the table */
    public void removeTemporaryOrderFromTable(int index){tableModel.removeRow(index);}


    /** Replaces the product at index "index" (list) */
    public void updateCurrentProductInList(Product product, int index){currentProducts.set(index,product);}
    /** Replaces the product at index "index" (dropdown) */
    public void updateCurrentProductInDropdown(Product product, int index) {
        productDropdown.removeItemAt(index);
        productDropdown.insertItemAt(product.toString(),index);
        productDropdown.setSelectedIndex(index);
    }


    /** Getter for temporaryOrders */
    public ArrayList<TemporaryOrder> getTemporaryOrders() {return temporaryOrders;}


    /** Adds action listeners to all 4 buttons */
    public void addActionListener(ActionListener al){
        addButton.addActionListener(al);
        removeButton.addActionListener(al);
        billButton.addActionListener(al);
        backButton.addActionListener(al);
    }


    /** Sets messageLabel - error or success messages */
    public void setMessage(String message){
        messageLabel.setText(message);
    }


    /** Brings the modifiable graphic components to their initial state */
    public void reset(){
        clientDropdown.setSelectedIndex(0);
        productDropdown.setSelectedIndex(0);
        quantityField.setText("");
        yearDropdown.setSelectedIndex(0);
        monthDropdown.setSelectedIndex(0);
        dayDropdown.setSelectedIndex(0);
        tableModel.setRowCount(0);
        messageLabel.setText("");
    }
}
