package business_logic.validators;

import business_logic.InvalidInputException;

/** Interface giving the minimum requirements for validators of all classes */
public interface Validator<T>{
    /**
     * Validate before inserting entity
     * @param t entity to be inserted
     * @throws InvalidInputException if entity is invalid
     */
    void validateInsert(T t) throws InvalidInputException;

    /**
     * Validate before updating entity
     * @param t entity to be updated
     * @throws InvalidInputException if new entity is invalid
     */
    void validateUpdate(T t) throws InvalidInputException;

    /**
     * Validate before deleting entity
     * @param t entity to be deleted
     * @throws InvalidInputException if entity cannot be deleted (due to existing constraints)
     */
    void validateDelete(T t) throws InvalidInputException;
}
