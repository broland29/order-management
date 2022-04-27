package business_logic.validators;

import business_logic.InvalidInputException;
import model.Order;

import java.sql.Date;

public class OrderValidator implements Validator<Order>{

    @Override
    public void validateInsert(Order order){
        //no validation needed at insertion, since temporary orders already validated
    }

    @Override
    public void validateUpdate(Order order) throws InvalidInputException {
        int quantity = order.getQuantity();
        Date pickupBy = order.getPickupBy();

        if (quantity <= 0)
            throw new InvalidInputException("Invalid QUANTITY! (should be >= 1)");

        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        if (currentDate.after(pickupBy))
            throw new InvalidInputException("Invalid DATE! (should be in the future)");
    }

    @Override
    public void validateDelete(Order order){
        //no validation needed when deleting order
    }
}
