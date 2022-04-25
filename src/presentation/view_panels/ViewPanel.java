package presentation.view_panels;

import data_access.ColumnMetadata;
import data_access.GenericTableDAO;
import data_access.TableMetadata;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**Generic panel for viewing all clients/ products/ orders
 * <p>Generates a JTable based on the tables of the database</p>
 * <p>In case of order, it executes a custom query to make more sense to the user</p>
 * <p>For better outlook, column widths are custom for each table</p>
 * <p>Other than these extra features, everything is generic- reflection is used</p>
 */
public class ViewPanel extends JPanel {

    String tableName;

    private DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;
    JButton backButton;

    private TableMetadata tableMetadata;
    private ArrayList<ColumnMetadata> columnMetadata;

    /** Basic constructor */
    public ViewPanel(String tableName){
        this.tableName = tableName;

        this.setBorder(BorderFactory.createMatteBorder(50,200,100,200,new Color(250, 237, 205)));

        if (tableName.equals("orders")){

            tableMetadata = new TableMetadata("orders",6, GenericTableDAO.getOrdersHeight());
            columnMetadata = new ArrayList<>();
            columnMetadata.add(new ColumnMetadata("id","int"));
            columnMetadata.add(new ColumnMetadata("client_name","varchar"));
            columnMetadata.add(new ColumnMetadata("client_phone","varchar"));
            columnMetadata.add(new ColumnMetadata("product_name","varchar"));
            columnMetadata.add(new ColumnMetadata("#","int"));
            columnMetadata.add(new ColumnMetadata("pick_up_by","date"));
        }
        else {
            tableMetadata = GenericTableDAO.getTableMetadata(tableName);
            columnMetadata = GenericTableDAO.getColumnMetadata(tableName);
        }

        String[] columnNames = new String[columnMetadata.size()];
        int i = 0;
        for (ColumnMetadata cm : columnMetadata){
            columnNames[i] = cm.columnName();
            i++;
        }

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 250));
        table.setFillsViewportHeight(true);

        Object[][] data = new Object[tableMetadata.height()][tableMetadata.width()];

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        table.setModel(tableModel);
        table.setBackground(new Color(250, 237, 205));
        table.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));

        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Color.WHITE);

        switch (tableName) {
            case "orders" -> {
                table.setPreferredScrollableViewportSize(new Dimension(600, 250));
                System.out.println("Dddd");
                //table.getColumnModel().getColumn(0).setPreferredWidth(10);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                table.getColumnModel().getColumn(0).setMinWidth(5);
                table.getColumnModel().getColumn(1).setMinWidth(130);
                table.getColumnModel().getColumn(2).setMinWidth(100);
                table.getColumnModel().getColumn(3).setMinWidth(230);
                table.getColumnModel().getColumn(4).setMinWidth(5);
                table.getColumnModel().getColumn(5).setMinWidth(90);
                table.getColumnModel().getColumn(5).setMaxWidth(Integer.MAX_VALUE);
            }
            case "clients" -> {
                table.setPreferredScrollableViewportSize(new Dimension(450, 250));
                table.getColumnModel().getColumn(0).setMinWidth(75);
                table.getColumnModel().getColumn(1).setMinWidth(275);
                table.getColumnModel().getColumn(2).setMinWidth(100);
            }
            //table.getColumnModel().getColumn(3).setMaxWidth(Integer.MAX_VALUE);
            case "products" -> {
                table.setPreferredScrollableViewportSize(new Dimension(500, 250));
                table.getColumnModel().getColumn(0).setMinWidth(75);
                table.getColumnModel().getColumn(1).setMinWidth(275);
                table.getColumnModel().getColumn(2).setMinWidth(75);
                table.getColumnModel().getColumn(3).setMinWidth(75);
                table.getColumnModel().getColumn(3).setMaxWidth(Integer.MAX_VALUE);
            }
        }

        scrollPane = new JScrollPane(table);

        this.setBackground(new Color(250, 237, 205));
        this.add(scrollPane);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150,36));
        backButton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        backButton.setFocusable(false);
        backButton.setBackground(new Color(233, 237, 201));
        backButton.setForeground(new Color(66, 37, 24));
        this.add(backButton);
    }

    /** Adds action listener to back button */
    public void addActionListener(ActionListener al){
        backButton.addActionListener(al);
    }

    /** Fills the table with the appropriate, up-to-date data */
    public void fillTable(){
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());

        if (tableName.equals("orders"))
            tableMetadata = new TableMetadata("orders",6, GenericTableDAO.getOrdersHeight());
        else
            tableMetadata = GenericTableDAO.getTableMetadata(tableName);

        Object[][] data = GenericTableDAO.getData(tableMetadata,columnMetadata);
        tableModel.setRowCount(0);
        for(Object[] row : data){
            tableModel.addRow(row);
        }
    }
}