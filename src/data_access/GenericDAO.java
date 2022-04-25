package data_access;

import model.Model;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static data_access.GenericHelperDAO.*;

/** Class containing generic query-related methods
 * <p>Can be used for orders, clients and products as well</p>
 */
public class GenericDAO<T extends Model>{

    Class<T> modelClass;

    /**
     * One and only constructor
     * @param modelClass class of the type of entity the DAO will be used on
     */
    public GenericDAO(Class<T> modelClass) {this.modelClass = modelClass;}


    /**Search based of the given object and count the matching rows
     *
     * @param filter the entity based on which we search
     * @return the number of entities (of same class as filter) obeying the requirements
     */
    public int searchAndCount(T filter) {
        String tableName = getAssociatedTable(modelClass);

        String searchStatementBuilder =
                "SELECT COUNT(*) FROM "                         +       // example of a working query:
                        tableName                               +       //  SELECT COUNT(*)
                        " WHERE "                               +       //    FROM clients
                        fieldsToString(filter, " AND ") ;       //   WHERE name = 'Johnny Test' AND phone = '0722038495'

        System.out.println(searchStatementBuilder);

        return (int)executeAndGetValue(searchStatementBuilder,"int");
    }


    /**
     * Create objects (of type Model) from a resultSet
     * <p>Based on sample code</p>
     * @param resultSet the resultSet of a query on selecting rows of a table from the database
     * @return a list of objects modelling the given rows
     * @throws RuntimeException in case there is no default constructor in modelling class
     */
    public ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> list = new ArrayList<>();

        Constructor<?>[] constructors = modelClass.getDeclaredConstructors();
        Constructor<?> constructor = null;
        for (Constructor<?> item : constructors) {
            constructor = item;
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }

        if(constructor == null){
            throw new RuntimeException("No default constructor");
        }

        try {
            while (resultSet.next()) {
                constructor.setAccessible(true);
                T instance = modelClass.cast(constructor.newInstance());
                for (Field field : modelClass.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(camelCaseToUnderscore(fieldName));
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, modelClass);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }


    /** Gets all rows associated with the given type */
    public ArrayList<T> getExistingEntities() {
        ResultSet resultSet = executeAndGetResultSet("SELECT * FROM " + getAssociatedTable(modelClass));
        return createObjects(resultSet);
    }


    /**
     * Basic update query
     * @param oldObject the "old version" of the entity
     * @param newObject the "new version" of the entity
     */
    public void update(T oldObject, T newObject){

        if (searchAndCount(oldObject) < 1)
            throw new RuntimeException("No such object");

        String tableName = getAssociatedTable(modelClass);

        String updateStatement =
                "UPDATE "                                           +   //example of a working query:
                        tableName                                   +   //  UPDATE clients
                        " SET "                                     +   //     SET name = 'Dummy The Newest', phone = '0788392011'
                        fieldsToString(newObject, ", ")     +   //   WHERE name = 'Johnny Test2' AND phone = '0722038496'
                        " WHERE "                                   +
                        fieldsToString(oldObject, " AND ")  ;

        executeUpdateCustom(updateStatement);
    }


    /**
     * Basic insert query
     * @param toInsert the entity to insert
     */
    public void insert(T toInsert){

        if (searchAndCount(toInsert) > 0)                                           //example of a working query
            throw new IllegalArgumentException("Object already exists");            //  INSERT INTO clients(client_id, name, phone)
                                                                                    //  VALUES ('102','my name','0722029933')
        String tableName = getAssociatedTable(modelClass);

        //get data needed for the query; example form: (field1,field2,field3)
        StringBuilder fieldNamesBuilder = new StringBuilder();
        StringBuilder fieldValuesBuilder = new StringBuilder();
        fieldNamesBuilder.append("(");
        fieldValuesBuilder.append("(");

        for(Field field : modelClass.getDeclaredFields()){
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(toInsert);

                fieldNamesBuilder.append(camelCaseToUnderscore(field.getName())).append(",");
                fieldValuesBuilder.append("'").append(value.toString()).append("',");
            }
            catch (IllegalAccessException iae){
                iae.printStackTrace();
            }
        }

        //replace last "," with ")"
        int i = fieldNamesBuilder.length() - 1;     //index of last character
        fieldNamesBuilder.replace(i,i+1,")");
        i = fieldValuesBuilder.length() - 1;
        fieldValuesBuilder.replace(i,i+1,")");

        String insertStatementString = "INSERT INTO " + tableName + " " + fieldNamesBuilder + " VALUES " + fieldValuesBuilder;
        GenericHelperDAO.executeUpdateCustom(insertStatementString);
    }


    /**
     * Basic delete query
     * @param toDelete the entity to delete
     */
    public void delete(T toDelete) {

        //could have checked if executeUpdate returns 0, but this seems more correct
        if (searchAndCount(toDelete) < 1)
            throw new IllegalArgumentException("No such object");

        String tableName = getAssociatedTable(modelClass);

        String deleteStatementBuilder =
                "DELETE FROM "                                      +       //example of a working query:
                        tableName                                   +       //  DELETE FROM clients
                        " WHERE "                                   +       //   WHERE name = 'hardcoded dummy dum' AND phone='0722029933'
                        fieldsToString(toDelete, " AND ")   ;

        executeUpdateCustom(deleteStatementBuilder);
    }


    /**
     * Get the biggest ID from table associated with the given class
     * <p>Useful when inserting new entity ("auto-increment")</p>
     * @return the biggest ID
     */
    public int getHighestID(){
        String tableName = getAssociatedTable(modelClass);          //example of a working query:
        String id;                                                  //  SELECT client_id
        switch (tableName){                                         //    FROM clients
            case "clients" -> id = "client_id";                     //   ORDER BY client_id DESC
            case "orders" -> id = "order_id";                       //   LIMIT 1;
            case "products" -> id = "product_id";
            default -> throw new IllegalArgumentException("ERROR in getHighestID");
        }
        String query = "SELECT " + id + " FROM " + tableName + " ORDER BY " + id + " DESC LIMIT 1";
        return (int)executeAndGetValue(query,"int");
    }
}
