package model;

import business_logic.OrderBL;

import java.sql.Date;

/**Models a row from table "orders"
 * <p>Field names are the same, but respecting java naming conventions</p>
 * <p>Field types are "mapped" from MySQL to Java</p>
 **/
public class Order implements Model {
    private int orderID;
    private int clientID;
    private int productID;
    private int quantity;
    private Date pickupBy;


    /** Empty constructor - DO NOT REMOVE - NEEDED FOR REFLECTION */
    public Order(){}


    /** Basic constructor */
    public Order(int orderID, int clientID, int productID, int quantity, Date pickupBy) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
        this.pickupBy = pickupBy;
    }


    /** Getter for orderID */
    public int getOrderID() {return orderID;}


    /** Getter for clientID - DO NOT REMOVE - USED IN createObjects */
    public int getClientID() {return clientID;}


    /** Getter for productID - DO NOT REMOVE - USED IN createObjects */
    public int getProductID() {return productID;}


    /** Getter for quantity */
    public int getQuantity() {return quantity;}


    /** Getter for pickupBy */
    public Date getPickupBy() {return pickupBy;}


    /** Setter for orderID - DO NOT REMOVE - USED IN createObjects */
    public void setOrderID(int orderID) {this.orderID = orderID;}


    /** Setter for clientID - DO NOT REMOVE - USED IN createObjects */
    public void setClientID(int clientID) {this.clientID = clientID;}


    /** Setter for productID - DO NOT REMOVE - USED IN createObjects */
    public void setProductID(int productID) {this.productID = productID;}


    /** Setter for quantity */
    public void setQuantity(int quantity) {this.quantity = quantity;}


    /** Setter for pickUpBy - DO NOT REMOVE - USED IN createObjects */
    public void setPickupBy(Date pickupBy) {this.pickupBy = pickupBy;}


    /** Overridden toString method, showing data of interest*/
    @Override
    public String toString() {
        OrderBL orderBL = new OrderBL();
        return orderID + " : " + orderBL.getNiceForm(orderID);
    }
}
