package business_logic;

import business_logic.validators.ClientValidator;
import data_access.ClientDAO;
import data_access.GenericDAO;
import model.Client;

import java.util.ArrayList;

/** Business Logic class for Clients - further logic and validations */
public class ClientBL {

    private final ClientValidator clientValidator;
    private final ClientDAO clientDAO;
    private final GenericDAO<Client> clientGenericDAO;


    /** Basic constructor */
    public ClientBL() {
        clientValidator = new ClientValidator();
        clientDAO = new ClientDAO();
        clientGenericDAO = new GenericDAO<>(Client.class);
    }


    /**
     * Update query on Client
     * @param oldClient "old version" of client
     * @param newClient "new version" of client
     * @throws InvalidInputException if new client is invalid
     */
    public void update(Client oldClient, Client newClient) throws InvalidInputException {
        clientValidator.validateUpdate(newClient);
        clientGenericDAO.update(oldClient,newClient);
    }


    /**
     * Delete query on Client
     * @param client client to delete
     * @throws InvalidInputException if client cannot be deleted
     */
    public void delete(Client client) throws InvalidInputException {
        clientValidator.validateDelete(client);
        clientGenericDAO.delete(client);
    }


    /**
     * Insert query on Client
     * @param client client to insert
     * @throws InvalidInputException if client is invalid
     */
    public void insert(Client client) throws InvalidInputException{
        clientValidator.validateInsert(client);
        clientGenericDAO.insert(client);
    }


    /** Get a list containing all clients in the database at the moment */
    public ArrayList<Client> getClients(){return clientGenericDAO.getExistingEntities();}


    /** Get the highest ID */
    public int getHighestID(){return clientGenericDAO.getHighestID();}


    /** Get the ID of Client based on its name */
    public int getID(String phone){return clientDAO.getID(phone);}

}
