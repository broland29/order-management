package model;

import java.sql.Date;

/**Record to hold temporarily a sub-order
 * <p>One order (bill) may hold multiple sub-orders</p>
 * <p>While in the orderAddPanel, these are stored as TemporaryOrders</p>
 * <p>When the bill is generated, these are transformed into actual Order objects and inserted into the database</p>
 **/
public record TemporaryOrder(Client client, Product product, int quantity, Date pickupBy, float price) {}
