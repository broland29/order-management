package data_access;

import static data_access.GenericHelperDAO.executeAndGetValue;

/** Class containing all Client-specific query-related methods */
public class ClientDAO {

    /**
     * Get the ID of a Client
     * @param phone the phone number associated with the client
     * @return the ID of the client associated with the phone number
     */
    public int getID(String phone) {
        String getIdStatement = "SELECT client_id FROM clients WHERE phone = " + phone;
        return (int)executeAndGetValue(getIdStatement,"int");
    }
}
