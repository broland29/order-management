package business_logic;

import business_logic.validators.TemporaryOrderValidator;
import model.TemporaryOrder;

/** Business Logic class for TemporaryOrders - further logic and validations */
public class TemporaryOrderBL {

    TemporaryOrderValidator temporaryOrderValidator;

    public TemporaryOrderBL(){
        temporaryOrderValidator = new TemporaryOrderValidator();
    }

    //there are no UPDATES on temporary orders

    //there is no need to validate when DELETING temporary orders

    /**
     * Validate before inserting
     * @param newTemporaryOrder temporary order to validate
     * @throws InvalidInputException if temporary order is not valid
     */
    public void validateInsert(TemporaryOrder newTemporaryOrder) throws InvalidInputException {
        temporaryOrderValidator.validateInsert(newTemporaryOrder);
    }
}
