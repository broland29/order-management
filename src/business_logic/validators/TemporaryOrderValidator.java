package business_logic.validators;

import business_logic.InvalidInputException;
import data_access.ProductDAO;
import model.Client;
import model.Product;
import model.TemporaryOrder;

import java.sql.Date;

public class TemporaryOrderValidator implements Validator<TemporaryOrder>{
    ProductDAO productDAO;

    public TemporaryOrderValidator(){
        productDAO = new ProductDAO();
    }

    @Override
    public void validateInsert(TemporaryOrder temporaryOrder) throws InvalidInputException {
        Product product = temporaryOrder.product();
        int quantity = temporaryOrder.quantity();
        Date pickupBy = temporaryOrder.pickupBy();

        if (quantity <= 0)
            throw new InvalidInputException("Invalid QUANTITY! (should be >= 1)");

        if (product.getQuantity() < quantity)
            throw new InvalidInputException("Invalid QUANTITY! (" + product.getQuantity() + " in stock)");

        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        if (currentDate.after(pickupBy))
            throw new InvalidInputException("Invalid DATE! (should be in the future)");
    }

    @Override
    public void validateUpdate(TemporaryOrder temporaryOrder) throws InvalidInputException {
        //update operation not implemented for temporary orders
    }

    @Override
    public void validateDelete(TemporaryOrder temporaryOrder){
        //no validations needed when temporary order is deleted
    }
}
