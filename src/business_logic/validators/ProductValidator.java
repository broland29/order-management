package business_logic.validators;

import business_logic.InvalidInputException;
import data_access.GenericDAO;
import data_access.ProductDAO;
import model.Order;
import model.Product;

import java.sql.Date;
import java.util.regex.Pattern;

public class ProductValidator implements Validator<Product>{
    private static final int MAX_NAME_LENGTH = 200;
    private static final int MIN_NAME_LENGTH = 1;

    ProductDAO productDAO;
    GenericDAO<Product> productGenericDAO;
    GenericDAO<Order> orderGenericDAO;

    /** Basic constructor */
    public ProductValidator(){
        productDAO = new ProductDAO();
        productGenericDAO = new GenericDAO<>(Product.class);
        orderGenericDAO = new GenericDAO<>(Order.class);
    }

    @Override
    public void validateInsert(Product productToInsert) throws InvalidInputException {

        String name = productToInsert.getName();
        int quantity = productToInsert.getQuantity();
        float price = productToInsert.getPrice();

        if (!Pattern.matches("[a-zA-Z0-9 ]*",name))
            throw new InvalidInputException("NAME contains illegal characters!");

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
            throw new InvalidInputException("Invalid NAME length! [" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH+ "]");

        if (quantity < 0)
            throw new InvalidInputException("Invalid QUANTITY! (should be >= 0)");

        if (price <= 0)
            throw new InvalidInputException("Invalid PRICE! (should be > 0)");
    }

    @Override
    public void validateUpdate(Product newProduct) throws InvalidInputException {
        validateInsert(newProduct);
    }

    @Override
    public void validateDelete(Product productToDelete) throws InvalidInputException {
        int productID = productToDelete.getProductID();
        if (orderGenericDAO.searchAndCount(new Order(-1,-1,productID,-1,new Date(0))) > 0)
            throw new InvalidInputException("Cannot delete PRODUCT since it has an active order!");
    }
}
