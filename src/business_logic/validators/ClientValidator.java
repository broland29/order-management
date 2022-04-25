package business_logic.validators;

import business_logic.InvalidInputException;
import data_access.ClientDAO;
import data_access.GenericDAO;
import model.Client;
import model.Order;

import java.sql.Date;
import java.util.regex.Pattern;

/** Validates a Client */
public class ClientValidator implements Validator<Client>{

    ClientDAO clientDAO;
    GenericDAO<Client> clientGenericDAO;
    GenericDAO<Order> orderGenericDAO;

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 1;

    /** Basic constructor */
    public ClientValidator(){
        clientDAO = new ClientDAO();
        clientGenericDAO = new GenericDAO<>(Client.class);
        orderGenericDAO = new GenericDAO<>(Order.class);
    }

    @Override
    public void validateInsert(Client clientToInsert) throws InvalidInputException{
        String name = clientToInsert.getName();
        String phone = clientToInsert.getPhone();

        if (!Pattern.matches("[a-zA-Z ]*",name))
            throw new InvalidInputException("Name contains illegal characters!");

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
            throw new InvalidInputException("Invalid NAME length! [" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH+ "]");

        if (!Pattern.matches("[0-9]*",phone))
            throw new InvalidInputException("PHONE number should contain only digits!");

        if (phone.length() != 10)
            throw new InvalidInputException("Invalid PHONE number length! (it should be 10 digits)");

        if (!(phone.charAt(0) == '0' && phone.charAt(1) == '7'))
            throw new InvalidInputException("PHONE number should obey format 07????????");

        if (clientGenericDAO.searchAndCount(new Client(-1,"",phone)) != 0)
            throw new InvalidInputException("PHONE number already in use!");
    }

    @Override
    public void validateUpdate(Client newClient) throws InvalidInputException {
        validateInsert(newClient);
    }

    @Override
    public void validateDelete(Client clientToDelete) throws InvalidInputException {
        int clientID = clientToDelete.getClientID();
        if (orderGenericDAO.searchAndCount(new Order(-1,clientID,-1,-1,new Date(0))) > 0)
            throw new InvalidInputException("Cannot delete CLIENT since it has an active order!");
    }
}
