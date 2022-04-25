package data_access;

import connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**Class containing all methods needed to generate a JTable based on a table from the database
 * <p>Everything is generic, reflection is used</p>
 */
public class GenericTableDAO {

    private final static String getAllQueryIncomplete =
            "SELECT *   "       +
                    "  FROM "   ;

    private final static String getTableHeightQueryIncomplete =
            "SELECT count(*) AS row_count "     +
                    "  FROM "                   ;

    private final static String getTableWidthQueryIncomplete =
            "SELECT count(*) AS column_count "              +
                    "  FROM information_schema.columns "    +
                    " WHERE table_name = "                  ;

    private final static String getAllQueryForOrder =
            "SELECT o.order_id AS 'id',"                                +
                    "       c.name AS 'client_name',"                   +
                    "       c.phone AS 'client_phone',"                 +
                    "       p.name AS 'product_name',"                  +
                    "       o.quantity AS '#',"                         +
                    "       o.pickup_by AS 'pick_up_by'"                +
                    "  FROM orders o"                                   +
                    "  JOIN clients c on c.client_id = o.client_id"     +
                    "  JOIN products p on o.product_id = p.product_id"  +
                    " ORDER BY order_id"                                ;

    private final static String getColumnMetadataQueryIncomplete =
            "SELECT COLUMN_NAME AS column_name, "                       +
                    "       DATA_TYPE AS data_type, "                   +
                    "       CHARACTER_MAXIMUM_LENGTH AS max_length "    +
                    "  FROM INFORMATION_SCHEMA.COLUMNS "                +
                    " WHERE TABLE_NAME = "                              ;

    /**
     * Get the tableMetadata related to a table
     * @param tableName name of the table
     * @return tableMetadata related to the table
     */
    public static TableMetadata getTableMetadata(String tableName){
        int tableWidth = (int) GenericHelperDAO.executeAndGetValue(getTableWidthQueryIncomplete + "'" + tableName + "'","int");
        int tableHeight = (int) GenericHelperDAO.executeAndGetValue(getTableHeightQueryIncomplete + tableName,"int");

        return new TableMetadata(tableName,tableWidth,tableHeight);
    }


    /** Returns the height of the table for "orders" - needed only since I did modifications for orders table*/
    public static int getOrdersHeight(){
        return (int) GenericHelperDAO.executeAndGetValue(
                "SELECT count(*) FROM orders" ,"int");
    }

    /**
     * Get the columnMetadata related to a table
     * @param tableName name of the table
     * @return columnMetadata related to the table
     */
    public static ArrayList<ColumnMetadata> getColumnMetadata(String tableName){
        ArrayList<ColumnMetadata> cm = new ArrayList<>();

        String getColumnMetadataQuery = getColumnMetadataQueryIncomplete + "'" + tableName + "'";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        String columnName;
        String columnClass;

        try {
            preparedStatement = connection.prepareStatement(getColumnMetadataQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                //gather info
                columnName = resultSet.getString("column_name");
                columnClass = resultSet.getString("data_type");
                //store info
                cm.add(new ColumnMetadata(columnName,columnClass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cm;
    }


    /**
     * Get (future) contents of a given table
     * @param tableMetadata tableMetadata related to the table
     * @param columnMetadata columnMetadata related to the table
     * @return two-dimensional Object array, useful in constructor of jTable
     */
    public static Object[][] getData(TableMetadata tableMetadata, ArrayList<ColumnMetadata> columnMetadata){

        Object[][] toReturn = new Object[tableMetadata.height()][tableMetadata.width()];

        String getAllQuery;
        if (tableMetadata.name().equals("orders"))
            getAllQuery = getAllQueryForOrder;
        else
            getAllQuery = getAllQueryIncomplete + tableMetadata.name();

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement showStatement;
        ResultSet resultSet;

        try {
            showStatement = connection.prepareStatement(getAllQuery);
            resultSet = showStatement.executeQuery();

            int row = 0;
            String columnName;
            String columnClass;

            //iterate rows
            while(resultSet.next()){

                int col = 0;
                //iterate columns of current row
                for (ColumnMetadata cm : columnMetadata){
                    columnName = cm.columnName();
                    columnClass = cm.columnClass();

                    switch (columnClass) {
                        case "int" -> toReturn[row][col] = resultSet.getInt(columnName);
                        case "varchar" -> toReturn[row][col] = resultSet.getString(columnName);
                        case "float" -> toReturn[row][col] = resultSet.getFloat(columnName);
                        case "date" -> toReturn[row][col] = resultSet.getDate(columnName);
                        default -> throw new UnsupportedOperationException("Unsupported class");
                    }
                    col++;
                }
                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}
