package data_access;

import connection.ConnectionFactory;
import model.Model;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** A static helper class for Data Access Objects */
public class GenericHelperDAO {

    /**
     * Convert camel case String to underscore string
     * @param string camelCase input
     * @return under_score output
     */
    public static String camelCaseToUnderscore(String string){
        Matcher matcher = Pattern.compile("(?<=[a-z])[A-Z]").matcher(string);

        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()){
            matcher.appendReplacement(stringBuilder,"_"+matcher.group().toLowerCase());
        }

        matcher.appendTail(stringBuilder);

        /*  toLowerCase needed since there are cases when we encounter uppercase values not preceded by lowercase values;
         *  in such a case we should not insert a "_", but we should make it lowercase nevertheless
         *  practical example: possible outcomes of clientID
         *          - client_i_d: if we don't check for lowercase precedence
         *          - client_iD:  if we don't use toLowerCase
         *          - client_id:  correct output
         */
        return stringBuilder.toString().toLowerCase();
    }


    /**
     * In some of my implementations, "blank" values of fields are possible
     * <p>This methods checks if the input is considered blank or not</p>
     * @param field the input field
     * @return is input blank (true) or not (false)
     * @throws RuntimeException if mapping cannot be made (should never occur)
     */
    static boolean isBlank(Object field){
        String classString = field.getClass().getSimpleName();
        return switch (classString) {
            case "Integer" -> field.equals(-1);
            case "Float" -> field.equals((float) -1);
            case "String" -> field.equals("");
            case "Date" -> field.equals(new java.sql.Date(0));
            default -> throw new RuntimeException("No such mapping supported");
        };
    }


    /**
     * In many cases, we need the fields of an object in a query
     * <p>This method converts the fields of an object into a String</p>
     * @param object the object whose fields need to be transformed
     * @param delimiter the delimiter put between the fields
     * @return the String representation
     * @throws RuntimeException if every field of the object is blank (should never occur)
     */
    static String fieldsToString(Object object, String delimiter){
        StringBuilder stringBuilder = new StringBuilder();
        Class<?> myClass = object.getClass();

        boolean first = true;

        for (Field field : myClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);

                if (isBlank(value))
                    continue;

                if (first) {
                    first = false;
                } else {
                    stringBuilder.append(delimiter);
                }

                stringBuilder.append(camelCaseToUnderscore(field.getName())).append(" = '").append(value).append("'");

            } catch (IllegalAccessException iae) {
                iae.printStackTrace();
            }
        }

        //to avoid executing an incorrect query if every field is blank
        if(first)
            throw new RuntimeException("Empty object!");

        return stringBuilder.toString();
    }


    /**
     * Executes a query and return its "result" value
     * <p>Unfortunately still need to cast afterwards</p>
     * @param statementString the query in String form
     * @param valueClassString the desired type of result
     * @return the (singleton) result of the query
     * @throws RuntimeException if mapping is not implemented for given type
     * @throws RuntimeException if query is bad (or something occurs and execution fails)
     */
    static Object executeAndGetValue(String statementString, String valueClassString){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement searchStatement = null;
        ResultSet resultSet;

        try {
            searchStatement = connection.prepareStatement(statementString);
            resultSet = searchStatement.executeQuery();
            resultSet.next();

            return switch (valueClassString) {
                case "int" -> resultSet.getInt(1);
                case "Float" -> resultSet.getFloat(1);
                case "String" -> resultSet.getString(1);
                case "Date" -> resultSet.getDate(1);
                default -> throw new RuntimeException("No such mapping supported");
            };

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't execute query");
        } finally {
            ConnectionFactory.close(searchStatement);
            ConnectionFactory.close(connection);
        }
    }


    /**
     * Executes a query and returns the resultSet
     * @param statementString the query in String form
     * @return the resultSet gotten from executing the query
     * @throws RuntimeException if query is bad (or something occurs and execution fails)
     */
    static ResultSet executeAndGetResultSet(String statementString){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement searchStatement;
        ResultSet resultSet;

        try {
            searchStatement = connection.prepareStatement(statementString);
            resultSet = searchStatement.executeQuery();
            return resultSet;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't execute query");
        }
    }


    /**
     * Executes an update statement (query)
     * @param statementString the query in String form
     * @throws RuntimeException if query is bad (or something occurs and execution fails)
     */
    static void executeUpdateCustom(String statementString){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        try{
            insertStatement = connection.prepareStatement(statementString);
            insertStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Couldn't execute query");
        }finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Gets the table associated with the given type
     * @param myClass the class for which we want the table representative
     * @return the table representative
     * @throws RuntimeException if mapping is not implemented
     */
    static String getAssociatedTable(Class <? extends Model> myClass){
        String classString = myClass.getSimpleName();
        return switch (classString) {
            case "Client" -> "clients";
            case "Order" -> "orders";
            case "Product" -> "products";
            default -> throw new RuntimeException("No such mapping supported");
        };
    }
}
