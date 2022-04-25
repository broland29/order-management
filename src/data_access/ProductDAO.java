package data_access;

import static data_access.GenericHelperDAO.executeAndGetValue;

/** Class containing all Product-specific query-related methods */
public class ProductDAO {

    /**
     * Get the ID of a Product
     * @param name the name associated with the product
     * @return the ID of the product associated with the name
     */
    public int getID(String name){
        String getIdStatement = "SELECT product_id FROM products WHERE name = '" + name + "'";
        return (int)executeAndGetValue(getIdStatement,"int");
    }
}
