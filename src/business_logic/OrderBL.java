package business_logic;

import business_logic.validators.OrderValidator;
import data_access.GenericDAO;
import data_access.OrderDAO;
import model.Order;
import model.TemporaryOrder;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/** Business Logic class for Orders - further logic and validations */
public class OrderBL {

    private final OrderValidator orderValidator;
    private final OrderDAO orderDAO;
    private final GenericDAO<Order> orderGenericDAO;

    public static final String[] YEAR_OPTIONS = {"2022", "2023", "2024"};
    public static final String[] MONTH_OPTIONS = {"Jan.", "Feb.", "Mar.", "Apr.", "May", "June", "July", "Aug.", "Sept.", "Oct.", "Nov.", "Dec."};
    public static final String[] DAY_OPTIONS = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

    private int billNo = 1;

    /** Basic constructor */
    public OrderBL(){
        orderValidator = new OrderValidator();
        orderDAO = new OrderDAO();
        orderGenericDAO = new GenericDAO<>(Order.class);
    }


    /**
     * Update query on Order
     * @param oldOrder "old version" of order
     * @param newOrder "new version" of order
     * @throws InvalidInputException if new order is invalid
     */
    public void update(Order oldOrder, Order newOrder) throws InvalidInputException {
        orderValidator.validateUpdate(newOrder);
        orderGenericDAO.update(oldOrder,newOrder);
    }


    /**
     * Delete query on Order
     * @param order the order to delete
     * @throws InvalidInputException if order cannot be deleted
     */
    public void delete(Order order) throws InvalidInputException {
        //no validation needed since temporary orders are already validated
        orderGenericDAO.delete(order);
    }


    /**
     * Insert query on Order
     * @param order the order to insert
     * @throws InvalidInputException if order is invalid
     */
    public void insert(Order order) throws InvalidInputException {
        //no validation needed since temporary orders are already validated
        orderGenericDAO.insert(order);
    }


    /** Get a list containing all orders in the database at the moment */
    public ArrayList<Order> getOrders(){return orderGenericDAO.getExistingEntities();}


    /** Get the highest ID */
    public int getHighestID(){
        return orderGenericDAO.getHighestID();
    }


    /**
     * Make a String having a format appropriate for using java.sql.Date's constructor
     * @param year year
     * @param month month
     * @param day day
     * @return a string of form YYYY-MM-DD based on the given inputs
     */
    public static String convertToJDBCDateFormat(String year, String month, String day){
        String aux;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");

        aux = (Arrays.asList(MONTH_OPTIONS).indexOf(month) + 1) + "";
        if (aux.length() == 1)
            stringBuilder.append("0");
        stringBuilder.append(aux);
        stringBuilder.append("-");

        if (day.length() == 1)
            stringBuilder.append("0");
        stringBuilder.append(day);

        return stringBuilder.toString();
    }

    /**
     * Creates a text file and opens it up
     * @param temporaryOrders array of (temporary) orders to be represented on the bill
     */
    public void createBill(ArrayList<TemporaryOrder> temporaryOrders){
        float totalPrice = 0;
        try {
            String fileName = "bill_" + billNo++ + ".txt";

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            StringBuilder aux = new StringBuilder();

            aux.append("****************************** ORDER ").append(billNo).append(" ");
            aux.append("*".repeat(Math.max(0, 75 - aux.length())));
            aux.append("\n");
            bufferedWriter.write(aux.toString());

            for (TemporaryOrder to : temporaryOrders) {
                bufferedWriter.write("---------------------------------------------------------------------------\n");
                aux = new StringBuilder();
                aux.append("---------- Client Name: ").append(to.client().getName()).append(" ");
                aux.append("-".repeat(Math.max(0, 75 - aux.length())));
                aux.append("\n");
                bufferedWriter.write(aux.toString());

                aux = new StringBuilder();
                aux.append("---------- Product Name: ").append(to.product().getName()).append(" ");
                aux.append("-".repeat(Math.max(0, 75 - aux.length())));
                aux.append("\n");
                bufferedWriter.write(aux.toString());

                aux = new StringBuilder();
                aux.append("---------- Quantity: ").append(to.quantity()).append(" ");
                aux.append("-".repeat(Math.max(0, 75 - aux.length())));
                aux.append("\n");
                bufferedWriter.write(aux.toString());

                aux = new StringBuilder();
                aux.append("---------- Pick Up By: ").append(to.pickupBy()).append(" ");
                aux.append("-".repeat(Math.max(0, 75 - aux.length())));
                aux.append("\n");
                bufferedWriter.write(aux.toString());

                aux = new StringBuilder();
                aux.append("---------- Price: ").append(to.price()).append(" ");
                aux.append("-".repeat(Math.max(0, 75 - aux.length())));
                aux.append("\n");
                bufferedWriter.write(aux.toString());

                totalPrice += to.price();
            }
            bufferedWriter.write("---------------------------------------------------------------------------\n");

            bufferedWriter.write("***************************************************************************\n");
            bufferedWriter.write("---------------------------------------------------------------------------\n");
            aux = new StringBuilder();
            aux.append("-------------------- Total Price: ").append(totalPrice).append(" ");
            aux.append("-".repeat(Math.max(0, 75 - aux.length())));
            aux.append("\n");
            bufferedWriter.write(aux.toString());
            bufferedWriter.write("---------------------------------------------------------------------------\n");
            bufferedWriter.write("***************************************************************************\n");
            bufferedWriter.close();
            Desktop.getDesktop().open(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** get a user-friendly String representation of the order having id orderID */
    public String getNiceForm(int orderID){
        return orderDAO.niceForm(orderID);
    }
}
