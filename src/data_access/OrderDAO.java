package data_access;

import java.sql.ResultSet;
import java.sql.SQLException;

import static data_access.GenericHelperDAO.executeAndGetResultSet;

/** Class containing all Order-specific query-related methods */
public class OrderDAO {

    /**
     * Generates the user-friendly representation of an order
     * <p>A row from the "orders" table holds no valuable content for a user</p>
     * <p>A query needs to be executed in order to replace the IDs with actual names</p>
     * @param orderID the ID of the order
     * @return the nice, String form of the order
     */
    public String niceForm(int orderID){
        String query =
                "SELECT c.name, c.phone, p.name, o.quantity, o.pickup_by "      +
                        "  FROM orders o "                                      +
                        "  JOIN clients c on c.client_id = o.client_id "        +
                        "  JOIN products p on o.product_id = p.product_id"      +
                        " WHERE order_id = " + orderID                          ;

        ResultSet resultSet = executeAndGetResultSet(query);

        try {
            resultSet.next();
            return
                    resultSet.getString(1)          + " ("      +
                            resultSet.getString(2)  + ") - "    +
                            resultSet.getString(3)  + " ("      +
                            resultSet.getString(4)  + ") - "    +
                            resultSet.getString(5)              ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
