package model;

 /**Models a row from table "clients"
  * <p>Field names are the same, but respecting java naming conventions</p>
  * <p>Field types are "mapped" from MySQL to Java</p>
  **/
public class Client implements Model {
    private int clientID;
    private String name;
    private String phone;


    /** Empty constructor - DO NOT REMOVE - NEEDED FOR REFLECTION */
    public Client(){}


    /** Basic constructor */
    public Client(int clientId, String name, String phone) {
        this.clientID = clientId;
        this.name = name;
        this.phone = phone;
    }


    /** Getter for clientID */
    public int getClientID() {return clientID;}


     /** Getter for name */
    public String getName() {return name;}


    /** Getter for phone */
    public String getPhone() {return phone;}


    /** Setter for clientID - DO NOT REMOVE - USED IN createObjects */
    public void setClientID(int clientID) {this.clientID = clientID;}


    /** Setter for phone - DO NOT REMOVE - USED IN createObjects */
    public void setPhone(String phone) {this.phone = phone;}


    /** Setter for name */
    public void setName(String name) {this.name = name;}


    /** Overridden toString method, showing data of interest*/
    @Override
    public String toString() {return name + " (" + phone + ")";}

}
