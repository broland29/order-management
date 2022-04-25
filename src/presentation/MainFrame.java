package presentation;

import model.Client;
import model.Order;
import model.Product;
import presentation.add_panels.ClientAddPanel;
import presentation.add_panels.OrderAddPanel;
import presentation.add_panels.ProductAddPanel;
import presentation.delete_panels.DeletePanel;
import presentation.edit_panels.ClientEditPanel;
import presentation.edit_panels.OrderEditPanel;
import presentation.edit_panels.ProductEditPanel;
import presentation.option_panels.OptionPanel;
import presentation.view_panels.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import static presentation.PanelName.*;

/**The one and only main frame
 * <p>Holds all the panels inside its contentPane</p>
 * <p>Makes communication with Controller possible</p>
 */
public class MainFrame{

    private PanelName previousPanel;
    private PanelName currentPanel;

    JFrame mainFrame;
    JPanel contentPane;

    JPanel titlePanel;
    JPanel centerPanel;
    JPanel footerPanel;

    JLabel titleLabel;
    JLabel footerLabel;

    CardLayout cardLayout;
    private final WelcomePanel welcomePanel;

    private final OptionPanel clientOptionPanel;
    private final OptionPanel orderOptionPanel;
    private final OptionPanel productOptionPanel;

    private final ClientAddPanel clientAddPanel;
    private final OrderAddPanel orderAddPanel;
    private final ProductAddPanel productAddPanel;

    private final DeletePanel<Client> clientDeletePanel;
    private final DeletePanel<Order> orderDeletePanel;
    private final DeletePanel<Product> productDeletePanel;

    private final ClientEditPanel clientEditPanel;
    private final OrderEditPanel orderEditPanel;
    private final ProductEditPanel productEditPanel;

    private final ViewPanel clientViewPanel;
    private final ViewPanel orderViewPanel;
    private final ViewPanel productViewPanel;

    /** Basic constructor */
    public MainFrame(){
        currentPanel = WELCOME_PANEL;

        mainFrame = new JFrame("Orders Management Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700,600);
        mainFrame.setResizable(false);


        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(250, 237, 205));


        titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBorder(BorderFactory.createMatteBorder(25,0,25,0,new Color(204, 213, 174)));
        titlePanel.setBackground(new Color(204, 213, 174));

        titleLabel = new JLabel("Orders Management Application");
        titleLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(66, 37, 24));

        titlePanel.add(titleLabel);


        centerPanel = new JPanel();
        cardLayout = new CardLayout();
        centerPanel.setLayout(cardLayout);

        welcomePanel = new WelcomePanel();

        clientOptionPanel = new OptionPanel("Client");
        orderOptionPanel = new OptionPanel("Order");
        productOptionPanel = new OptionPanel("Product");

        clientAddPanel = new ClientAddPanel();
        orderAddPanel = new OrderAddPanel();
        productAddPanel = new ProductAddPanel();

        clientDeletePanel = new DeletePanel<>();
        orderDeletePanel = new DeletePanel<>();
        productDeletePanel = new DeletePanel<>();

        clientEditPanel = new ClientEditPanel();
        orderEditPanel = new OrderEditPanel();
        productEditPanel = new ProductEditPanel();

        clientViewPanel = new ViewPanel("clients");
        orderViewPanel = new ViewPanel("orders");
        productViewPanel = new ViewPanel("products");

        centerPanel.add(welcomePanel,WELCOME_PANEL.toString());

        centerPanel.add(clientOptionPanel,CLIENT_OPTION_PANEL.toString());
        centerPanel.add(orderOptionPanel,ORDER_OPTION_PANEL.toString());
        centerPanel.add(productOptionPanel,PRODUCT_OPTION_PANEL.toString());

        centerPanel.add(clientAddPanel,CLIENT_ADD_PANEL.toString());
        centerPanel.add(orderAddPanel,ORDER_ADD_PANEL.toString());
        centerPanel.add(productAddPanel,PRODUCT_ADD_PANEL.toString());

        centerPanel.add(clientDeletePanel,CLIENT_DELETE_PANEL.toString());
        centerPanel.add(orderDeletePanel,ORDER_DELETE_PANEL.toString());
        centerPanel.add(productDeletePanel,PRODUCT_DELETE_PANEL.toString());

        centerPanel.add(clientEditPanel,CLIENT_EDIT_PANEL.toString());
        centerPanel.add(orderEditPanel,ORDER_EDIT_PANEL.toString());
        centerPanel.add(productEditPanel,PRODUCT_EDIT_PANEL.toString());

        centerPanel.add(clientViewPanel,CLIENT_VIEW_PANEL.toString());
        centerPanel.add(orderViewPanel,ORDER_VIEW_PANEL.toString());
        centerPanel.add(productViewPanel,PRODUCT_VIEW_PANEL.toString());

        cardLayout.show(centerPanel, WELCOME_PANEL.toString());


        footerPanel = new JPanel();
        footerPanel.setBackground(new Color(204, 213, 174));
        footerLabel = new JLabel("Project realized by broland29");
        footerLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        footerLabel.setForeground(new Color(66, 37, 24));
        footerPanel.add(footerLabel);

        contentPane.add(titlePanel,BorderLayout.NORTH);
        contentPane.add(centerPanel,BorderLayout.CENTER);
        contentPane.add(footerPanel,BorderLayout.SOUTH);

        mainFrame.add(contentPane);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /** Switches the contentPane to panel "panelName" */
    public void switchCardLayout(PanelName panelName){
        previousPanel = currentPanel;
        currentPanel = panelName;
        cardLayout.show(centerPanel,currentPanel.toString());
    }

    /** Adds action listener to welcomePanel*/
    public void addWelcomeListener(ActionListener al){ welcomePanel.addActionListener(al);}

    /** Adds action listener to clientOptionPanel*/
    public void addClientOptionListener(ActionListener al) {clientOptionPanel.addActionListener(al);}
    /** Adds action listener to orderOptionPanel*/
    public void addOrderOptionListener(ActionListener al) {orderOptionPanel.addActionListener(al);}
    /** Adds action listener to productOptionPanel*/
    public void addProductOptionListener(ActionListener al) {productOptionPanel.addActionListener(al);}

    /** Adds action listener to clientAddPanel*/
    public void addClientAddListener(ActionListener al) {clientAddPanel.addActionListener(al);}
    /** Adds action listener to orderAddPanel*/
    public void addOrderAddListener(ActionListener al){orderAddPanel.addActionListener(al);}
    /** Adds action listener to productAddPanel*/
    public void addProductAddListener(ActionListener al) {productAddPanel.addActionListener(al);}

    /** Adds action listener to clientDeletePanel*/
    public void addClientDeleteListener(ActionListener al){clientDeletePanel.addActionListener(al);}
    /** Adds action listener to orderDeletePanel*/
    public void addOrderDeleteListener(ActionListener al){orderDeletePanel.addActionListener(al);}
    /** Adds action listener to productDeletePanel*/
    public void addProductDeleteListener(ActionListener al){productDeletePanel.addActionListener(al);}

    /** Adds action listener to clientEditPanel*/
    public void addClientEditListener(ActionListener al) {clientEditPanel.addActionListener(al);}
    /** Adds action listener to orderEditPanel*/
    public void addOrderEditListener(ActionListener al) {orderEditPanel.addActionListener(al);}
    /** Adds action listener to productEditPanel*/
    public void addProductEditListener(ActionListener al) {productEditPanel.addActionListener(al);}

    /** Adds action listener to clientViewPanel*/
    public void addClientViewListener(ActionListener al) {clientViewPanel.addActionListener(al);}
    /** Adds action listener to orderViewPanel*/
    public void addOrderViewListener(ActionListener al) {orderViewPanel.addActionListener(al);}
    /** Adds action listener to productViewPanel*/
    public void addProductViewListener(ActionListener al) {productViewPanel.addActionListener(al);}

    /** Getter for previousPanel */
    public PanelName getPreviousPanel() {return previousPanel;}

    /** Getter for clientOptionPanel*/
    public OptionPanel getClientOptionPanel() {return clientOptionPanel;}
    /** Getter for orderOptionPanel*/
    public OptionPanel getOrderOptionPanel() {return orderOptionPanel;}
    /** Getter for productOptionPanel*/
    public OptionPanel getProductOptionPanel() {return productOptionPanel;}

    /** Getter for orderAddPanel*/
    public OrderAddPanel getOrderAddPanel() {return orderAddPanel;}
    /** Getter for clientAddPanel*/
    public ClientAddPanel getClientAddPanel() {return clientAddPanel;}
    /** Getter for productAddPanel*/
    public ProductAddPanel getProductAddPanel() {return productAddPanel;}

    /** Getter for clientDeletePanel*/
    public DeletePanel<Client> getClientDeletePanel() {return clientDeletePanel;}
    /** Getter for orderDeletePanel*/
    public DeletePanel<Order> getOrderDeletePanel() {return orderDeletePanel;}
    /** Getter for productDeletePanel*/
    public DeletePanel<Product> getProductDeletePanel() {return productDeletePanel;}

    /** Getter for clientEditPanel*/
    public ClientEditPanel getClientEditPanel() {return clientEditPanel;}
    /** Getter for orderEditPanel*/
    public OrderEditPanel getOrderEditPanel() {return orderEditPanel;}
    /** Getter for productEditPanel*/
    public ProductEditPanel getProductEditPanel() {return productEditPanel;}

    /** Getter for clientViewPanel*/
    public ViewPanel getClientViewPanel() {return clientViewPanel;}
    /** Getter for orderViewPanel*/
    public ViewPanel getOrderViewPanel() {return orderViewPanel;}
    /** Getter for productViewPanel*/
    public ViewPanel getProductViewPanel() {return productViewPanel;}

    /** Adds mouse adapter to footerLabel */
    public void addMouseListener(MouseAdapter ma){footerLabel.addMouseListener(ma);}

    /** Sets color text of footerLabel to red */
    public void setHighlight(){footerLabel.setForeground(new Color(240, 107, 24));}

    /** Sets color text of footerLabel to brown */
    public void undoHighlight(){footerLabel.setForeground(new Color(66, 37, 24));}

}
