package presentation.controller;

import business_logic.*;

import model.*;

import presentation.MainFrame;
import presentation.add_panels.ClientAddPanel;
import presentation.add_panels.OrderAddPanel;
import presentation.add_panels.ProductAddPanel;
import presentation.delete_panels.DeletePanel;
import presentation.edit_panels.ClientEditPanel;
import presentation.edit_panels.OrderEditPanel;
import presentation.edit_panels.ProductEditPanel;
import presentation.option_panels.OptionPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;

import static presentation.PanelName.*;

/**Controller class: gets inputs from GUI, processes them
 * <p>Contains implementations of action listeners</p>
 * <p>Makes connection between Presentation layer and Business Logic layer</p>
 */
public class Controller {

    private final MainFrame mainFrame;

    ClientBL clientBL;
    OrderBL orderBL;
    ProductBL productBL;
    TemporaryOrderBL temporaryOrderBL;

    /** Instantiates aggregated helpers from Business Layer and puts on the action listeners */
    public Controller(){
        mainFrame = new MainFrame();

        clientBL = new ClientBL();
        orderBL = new OrderBL();
        productBL = new ProductBL();
        temporaryOrderBL = new TemporaryOrderBL();

        mainFrame.addWelcomeListener(new WelcomeListener());

        mainFrame.addClientOptionListener(new ClientOptionListener());
        mainFrame.addOrderOptionListener(new OrderOptionListener());
        mainFrame.addProductOptionListener(new ProductOptionListener());

        mainFrame.addClientAddListener(new ClientAddListener());
        mainFrame.addOrderAddListener(new OrderAddListener());
        mainFrame.addProductAddListener(new ProductAddListener());

        mainFrame.addClientDeleteListener(new ClientDeleteListener());
        mainFrame.addOrderDeleteListener(new OrderDeleteListener());
        mainFrame.addProductDeleteListener(new ProductDeleteListener());

        mainFrame.addClientEditListener(new ClientEditListener());
        mainFrame.addOrderEditListener(new OrderEditListener());
        mainFrame.addProductEditListener(new ProductEditListener());

        mainFrame.addClientViewListener(new GenericViewListener());
        mainFrame.addOrderViewListener(new GenericViewListener());
        mainFrame.addProductViewListener(new GenericViewListener());

        mainFrame.addMouseListener(new VisitMe());
    }

    //ActionListener for WelcomePanel
    private class WelcomeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Client Management" -> mainFrame.switchCardLayout(CLIENT_OPTION_PANEL);
                case "Order Management" -> mainFrame.switchCardLayout(ORDER_OPTION_PANEL);
                case "Product Management" -> mainFrame.switchCardLayout(PRODUCT_OPTION_PANEL);
                default -> System.out.println(e.getActionCommand());
            }
        }
    }

    //ActionListener for ClientOptionPanel
    private class ClientOptionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            OptionPanel op = mainFrame.getClientOptionPanel();
            if (e.getSource() == op.getAddButton()){
                mainFrame.getClientAddPanel().reset();
                mainFrame.switchCardLayout(CLIENT_ADD_PANEL);
            }
            else if (e.getSource() == op.getEditButton()){
                mainFrame.getClientEditPanel().setDropdown(clientBL.getClients());
                mainFrame.getClientEditPanel().reset();
                mainFrame.switchCardLayout(CLIENT_EDIT_PANEL);
            }
            else if (e.getSource() == op.getDeleteButton()){
                mainFrame.getClientDeletePanel().setDropdowns(clientBL.getClients());
                mainFrame.getClientDeletePanel().reset();
                mainFrame.switchCardLayout(CLIENT_DELETE_PANEL);
            }
            else if (e.getSource() == op.getViewAllButton()){
                mainFrame.getClientViewPanel().fillTable();
                mainFrame.switchCardLayout(CLIENT_VIEW_PANEL);
            }
            else if (e.getSource() == op.getBackButton()){
                mainFrame.switchCardLayout(WELCOME_PANEL);
            }
        }
    }

    //ActionListener for OrderOptionPanel
    private class OrderOptionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            OptionPanel op = mainFrame.getOrderOptionPanel();
            if (e.getSource() == op.getAddButton()){
                mainFrame.getOrderAddPanel().setDropdowns(clientBL.getClients(), productBL.getProducts());
                mainFrame.getOrderAddPanel().reset();
                mainFrame.switchCardLayout(ORDER_ADD_PANEL);
            }
            else if (e.getSource() == op.getEditButton()){
                mainFrame.getOrderEditPanel().setDropdowns(clientBL.getClients(), orderBL.getOrders(), productBL.getProducts());
                mainFrame.getOrderEditPanel().reset();//if reset before - "setSelectedIndex: 0 out of bounds"
                mainFrame.switchCardLayout(ORDER_EDIT_PANEL);
            }
            else if (e.getSource() == op.getDeleteButton()){
                mainFrame.getOrderDeletePanel().setDropdowns(orderBL.getOrders());
                mainFrame.getOrderDeletePanel().reset();
                mainFrame.switchCardLayout(ORDER_DELETE_PANEL);
            }
            else if (e.getSource() == op.getViewAllButton()){
                mainFrame.getOrderViewPanel().fillTable();
                mainFrame.switchCardLayout(ORDER_VIEW_PANEL);
            }
            else if (e.getSource() == op.getBackButton()){
                mainFrame.switchCardLayout(WELCOME_PANEL);
            }
        }
    }

    //ActionListener for ProductOptionPanel
    private class ProductOptionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            OptionPanel op = mainFrame.getProductOptionPanel();
            if (e.getSource() == op.getAddButton()){
                mainFrame.getProductAddPanel().reset();
                mainFrame.switchCardLayout(PRODUCT_ADD_PANEL);
            }
            else if (e.getSource() == op.getEditButton()){
                mainFrame.getProductEditPanel().setDropdown(productBL.getProducts());
                mainFrame.getProductEditPanel().reset();
                mainFrame.switchCardLayout(PRODUCT_EDIT_PANEL);
            }
            else if (e.getSource() == op.getDeleteButton()){
                mainFrame.getProductDeletePanel().setDropdowns(productBL.getProducts());
                mainFrame.getProductDeletePanel().reset();
                mainFrame.switchCardLayout(PRODUCT_DELETE_PANEL);
            }
            else if (e.getSource() == op.getViewAllButton()){
                mainFrame.getProductViewPanel().fillTable();
                mainFrame.switchCardLayout(PRODUCT_VIEW_PANEL);
            }
            else if (e.getSource() == op.getBackButton()){
                mainFrame.switchCardLayout(WELCOME_PANEL);
            }
        }
    }

    //ActionListener for any kind of ViewPanel
    private class GenericViewListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.switchCardLayout(mainFrame.getPreviousPanel());
        }
    }

    //ActionListener for ClientDeletePanel
    private class ClientDeleteListener implements ActionListener{

        DeletePanel<Client> cdp = mainFrame.getClientDeletePanel();

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cdp.getProceedButton()){
                int index = cdp.getSelectedIndex();
                Client toDelete = cdp.getEntity(index);

                try {
                    clientBL.delete(toDelete);
                } catch (InvalidInputException iie) {
                    cdp.setMessage(iie.getMessage());
                    return;
                }

                cdp.removeEntityFromList(index);
                cdp.removeEntityFromDropdown(index);
                cdp.setMessage("Success!");
            }
            else if (e.getSource() == cdp.getBackButton()){
                mainFrame.switchCardLayout(CLIENT_OPTION_PANEL);
            }
        }
    }

    //ActionListener for OrderDeletePanel
    class OrderDeleteListener implements ActionListener{

        DeletePanel<Order> odp = mainFrame.getOrderDeletePanel();

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == odp.getProceedButton()){
                int index = odp.getSelectedIndex();
                Order toDelete = odp.getEntity(index);

                try {
                    orderBL.delete(toDelete);
                } catch (InvalidInputException iie) {
                    odp.setMessage(iie.getMessage());
                    return;
                }

                odp.removeEntityFromList(index);
                odp.removeEntityFromDropdown(index);
                odp.setMessage("Success!");
            }
            else if (e.getSource() == odp.getBackButton()){
                mainFrame.switchCardLayout(ORDER_OPTION_PANEL);
            }
        }
    }

    //ActionListener for ProductDeletePanel
    class ProductDeleteListener implements ActionListener{

        DeletePanel<Product> pdp = mainFrame.getProductDeletePanel();

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pdp.getProceedButton()){
                int index = pdp.getSelectedIndex();
                Product toDelete = pdp.getEntity(index);

                toDelete.setPrice(-1);  //https://stackoverflow.com/questions/6135395/mysql-float-data-not-selecting-in-where-clause

                try {
                    productBL.delete(toDelete);
                } catch (InvalidInputException iie) {
                    pdp.setMessage(iie.getMessage());
                    return;
                }

                pdp.removeEntityFromList(index);
                pdp.removeEntityFromDropdown(index);
                pdp.setMessage("Success!");
            }
            else if (e.getSource() == pdp.getBackButton()){
                mainFrame.switchCardLayout(PRODUCT_OPTION_PANEL);
            }
        }
    }

    //ActionListener for ClientEditPanel
    class ClientEditListener implements ActionListener{

        ClientEditPanel cep = mainFrame.getClientEditPanel();
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cep.getProceedButton()){

                int clientID = -1;
                String name = cep.getNameText();
                String phone = cep.getPhoneText();
                Client newClient = new Client(clientID,name,phone);

                int index = cep.getSelectedIndex();
                Client oldClient = cep.getClient(index);

                try {
                    clientBL.update(oldClient,newClient);
                }catch (InvalidInputException iie){
                    cep.setMessage(iie.getMessage());
                    return;
                }

                cep.updateCurrentClientInList(newClient,index);
                cep.updateCurrentClientInDropdown(newClient,index);

                cep.setMessage("Success!");

            }
            else if (e.getSource() == cep.getBackButton()){
                mainFrame.switchCardLayout(CLIENT_OPTION_PANEL);
            }
        }
    }

    //ActionListener for OrderEditPanel
    class OrderEditListener implements ActionListener{

        OrderEditPanel oep = mainFrame.getOrderEditPanel();
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == oep.getProceedButton()){

                int clientIndex = oep.getSelectedIndexClient();
                int productIndex = oep.getSelectedIndexProduct();
                Client newClient = oep.getClient(clientIndex);
                Product newProduct = oep.getProduct(productIndex);

                int orderIndex = oep.getSelectedIndexOrder();
                Order oldOrder = oep.getOrder(orderIndex);

                /*  Cannot let orderID stay zero:
                 *      - we refresh order dropdown, order dropdown contains strings
                 *      - toString of orders calls niceForm, niceForm calls executeAndGetResultSet
                 *      - executeAndGetResultSet skips through index of -1, resultSet is smaller
                 *      - => java.sql.SQLException: Illegal operation on empty result set. (in niceForm)
                 */

                int orderID = oldOrder.getOrderID();
                int clientID = newClient.getClientID();
                int productID = newProduct.getProductID();
                int quantity;
                try {
                    quantity = oep.getQuantity();
                } catch (InvalidInputException iie) {
                    oep.setMessage(iie.getMessage());
                    return;
                }
                Date pickupBy = oep.getDate();
                Order newOrder = new Order(orderID,clientID,productID,quantity,pickupBy);

                try {
                    orderBL.update(oldOrder,newOrder);
                } catch (InvalidInputException iie) {
                    oep.setMessage(iie.getMessage());
                    return;
                }

                oep.updateCurrentOrderInList(newOrder,orderIndex);
                oep.updateCurrentOrderInDropdown(newOrder,orderIndex);
                oep.setMessage("Success!");
            }
            else if (e.getSource() == oep.getBackButton()){
                mainFrame.switchCardLayout(ORDER_OPTION_PANEL);
            }
        }
    }

    //ActionListener for ProductEditPanel
    class ProductEditListener implements ActionListener{

        ProductEditPanel pep = mainFrame.getProductEditPanel();
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pep.getProceedButton()){

                int productID = -1;
                String name = pep.getProductName();
                int quantity;
                float price;
                try {
                    quantity = pep.getProductQuantity();
                    price = pep.getProductPrice();
                } catch (InvalidInputException iie) {
                    pep.setMessage(iie.getMessage());
                    return;
                }

                Product newProduct = new Product(productID,name,quantity,price);

                int index = pep.getSelectedIndex();
                Product oldProduct = pep.getProduct(index);
                oldProduct.setPrice(-1);

                try {
                    productBL.update(oldProduct,newProduct);
                } catch (InvalidInputException iie) {
                    pep.setMessage(iie.getMessage());
                    return;
                }

                pep.updateCurrentProductInList(newProduct,index);
                pep.updateCurrentProductInDropdown(newProduct,index);
                pep.setMessage("Success!");
            }
            else if (e.getSource() == pep.getBackButton()){
                mainFrame.switchCardLayout(PRODUCT_OPTION_PANEL);
            }
        }
    }

    //ActionListener for OrderAddPanel
    class OrderAddListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            OrderAddPanel oap = mainFrame.getOrderAddPanel();

            if (e.getSource() == oap.getAddButton()){

                int clientIndex = oap.getSelectedIndexClient();
                int productIndex = oap.getSelectedIndexProduct();

                Client client = oap.getClient(clientIndex);
                Product product = oap.getProduct(productIndex);
                int quantity;
                try {
                    quantity = oap.getQuantity();
                } catch (InvalidInputException iie) {
                    oap.setMessage(iie.getMessage());
                    return;
                }
                Date pickupBy = oap.getDate();
                float price = product.getPrice() * quantity;

                TemporaryOrder temporaryOrder = new TemporaryOrder(client,product,quantity,pickupBy,price);

                try {
                    temporaryOrderBL.validateInsert(temporaryOrder);
                } catch (InvalidInputException iie) {
                    oap.setMessage(iie.getMessage());
                    return;
                }
                oap.addTemporaryOrderToList(temporaryOrder);
                oap.addTemporaryRowToTable(temporaryOrder);
                oap.setMessage(""); //delete previous error message, if there is such

                int oldQuantity = product.getQuantity();
                product.setQuantity(oldQuantity - temporaryOrder.quantity());
                oap.updateCurrentProductInList(product,productIndex);
                oap.updateCurrentProductInDropdown(product,productIndex);
            }
            else if (e.getSource() == oap.getRemoveButton()){

                int temporaryOrderIndex = oap.getSelectedIndexTemporaryOrder();
                int productIndex = oap.getSelectedIndexProduct();

                TemporaryOrder temporaryOrder = oap.getTemporaryOrder(temporaryOrderIndex);
                Product product = temporaryOrder.product();
                int oldQuantity = product.getQuantity();
                product.setQuantity(oldQuantity + temporaryOrder.quantity());

                oap.updateCurrentProductInList(product,productIndex);
                oap.updateCurrentProductInDropdown(product,productIndex);

                oap.removeTemporaryOrderFromList(temporaryOrderIndex);
                oap.removeTemporaryOrderFromTable(temporaryOrderIndex);
            }
            else if (e.getSource() == oap.getBillButton()){

                int orderID;
                int clientID;
                int productID;
                int quantity;
                Date pickupBy;
                ArrayList<TemporaryOrder> temporaryOrders = oap.getTemporaryOrders();

                for (TemporaryOrder to : temporaryOrders){
                    System.out.println(to);
                    orderID = orderBL.getHighestID() + 1;
                    clientID = clientBL.getID(to.client().getPhone());
                    productID = productBL.getID(to.product().getName());
                    quantity = to.quantity();
                    pickupBy = to.pickupBy();

                    Order order = new Order(orderID,clientID,productID,quantity,pickupBy);

                    try {
                        orderBL.insert(order);
                        productBL.update(
                                new Product(productID,"",-1,-1),
                                new Product(-1,to.product().getName(),to.product().getQuantity(),to.product().getPrice())//to pass validations, cannot leave quantity and price "blank"
                        );
                    } catch (InvalidInputException iie) {
                        oap.setMessage(iie.getMessage());
                        return;
                    }
                    //in this case, no need to update local data, since it was already updated
                    //to give the illusion of a fast program
                }

                orderBL.createBill(temporaryOrders);
                oap.reset();
                oap.setMessage("Success!");
            }
            else if (e.getSource() == oap.getBackButton()){
                mainFrame.switchCardLayout(ORDER_OPTION_PANEL);
            }
        }
    }

    //ActionListener for ClientAddPanel
    class ClientAddListener implements ActionListener{
        ClientAddPanel clientAddPanel = mainFrame.getClientAddPanel();

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clientAddPanel.getProceedButton()){
                int clientID = clientBL.getHighestID() + 1;
                String name = clientAddPanel.getNameText();
                String phone = clientAddPanel.getPhoneText();
                try {
                    clientBL.insert(new Client(clientID,name,phone));
                } catch (InvalidInputException iie) {
                    clientAddPanel.setMessage(iie.getMessage());
                    return;
                }
                clientAddPanel.setMessage("Success!");
            }
            else if (e.getSource() == clientAddPanel.getBackButton()){
                mainFrame.switchCardLayout(CLIENT_OPTION_PANEL);
            }
        }
    }

    //ActionListener for ProductAddPanel
    class ProductAddListener implements ActionListener{
        ProductAddPanel productAddPanel = mainFrame.getProductAddPanel();

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == productAddPanel.getProceedButton()){
                int productID = productBL.getHighestID() + 1;
                String name = productAddPanel.getProductName();
                int quantity;
                float price;
                try {
                    quantity = productAddPanel.getProductQuantity();
                    price = productAddPanel.getProductPrice();
                    productBL.insert(new Product(productID,name,quantity,price));
                } catch (InvalidInputException iie) {
                    productAddPanel.setMessage(iie.getMessage());
                    return;
                }
                productAddPanel.setMessage("Success!");
            }
            else if (e.getSource() == productAddPanel.getBackButton()){
                mainFrame.switchCardLayout(PRODUCT_OPTION_PANEL);
            }
        }
    }

    //MouseAdapter for FooterLabel
    class VisitMe extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/broland29"));

            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mainFrame.setHighlight();
        }

        @Override
        public void mouseExited(MouseEvent e) {
             mainFrame.undoHighlight();
         }
    }
}
