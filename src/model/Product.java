package model;

/**Models a row from table "products"
 * <p>Field names are the same, but respecting java naming conventions</p>
 * <p>Field types are "mapped" from MySQL to Java</p>
 **/
public class Product implements Model {
    private int productID;
    private String name;
    private int quantity;
    private float price;


    /** Empty constructor - DO NOT REMOVE - NEEDED FOR REFLECTION */
    public Product() {
    }


    /** Basic constructor */
    public Product(int productID, String name, int quantity, float price) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }


    /** Getter for productID */
    public int getProductID(){return productID;}


    /** Getter for name */
    public String getName(){return name;}


    /** Getter for quantity */
    public int getQuantity(){return quantity;}


    /** Getter for price */
    public float getPrice(){return price;}


    /** Setter for name */
    public void setName(String name){this.name = name;}


    /** Setter for quantity */
    public void setQuantity(int quantity){this.quantity = quantity;}


    /** Setter for price */
    public void setPrice(float price){this.price = price;}


    /** Overridden toString method, showing data of interest*/
    @Override
    public String toString(){return name + " (" + quantity + ")";}

}
