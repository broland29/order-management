package business_logic;

/** Custom exception to be thrown whenever user causes trouble through GUI */
public class InvalidInputException extends Exception{
    public InvalidInputException(String errorMessage){
        super(errorMessage);
    }
}
