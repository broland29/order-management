package connection;

import java.sql.*;

/** Class containing connection-related methods */
public class ConnectionFactory {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/order_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0000";

    private ConnectionFactory() {}

    private Connection createConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
        }catch (SQLException e){
            System.out.println("ERROR: could not connect to database.");
            e.printStackTrace();
        }
        return connection;
    }


    /** Get connection to database */
    public static Connection getConnection(){
        ConnectionFactory singleInstance = new ConnectionFactory();
        return singleInstance.createConnection();
    }


    /** Close a connection */
    public static void close(Connection connection){
        if (connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("ERROR: could not close connection.");
                e.printStackTrace();
            }
        }
    }


    /** Close a statement */
    public static void close(Statement statement){
        if (statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                System.out.println("ERROR: could not close statement.");
                e.printStackTrace();
            }
        }
    }
}
