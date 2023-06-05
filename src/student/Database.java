package student;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Database {

    public static final String DRIVER_PATH = "oracle.jdbc.driver.OracleDriver";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String USER = "system";
    public static final String PASSWORD = "admin";

    // public static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    // public static final String URL = "jdbc:mysql://localhost:3306/test";
    // public static final String USER = "root";
    // public static final String PASSWORD = "";

    public static final String SELECT_QUERY = "SELECT * FROM student";
    public static final String INSERT_QUERY = "INSERT INTO student VALUES(?, ?, ?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM student WHERE student_id = ?";
    public static final String UPDATE_QUERY = "UPDATE student " + 
                                              "SET student_name = ?,"+
                                              "student_email = ?, " +
                                              "phone = ? " +
                                              "WHERE student_id = ?";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DRIVER_PATH);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } 
        
        catch (SQLException e) {
            System.out.println("Failed to establish the connection");
            System.out.println(e);
        }

        catch (ClassNotFoundException e) {
            System.out.println("Failed to load the mysql driver");
            System.out.println(e);
        }

        return connection;
    }

    public static void loadTable(DefaultTableModel tableModel) {

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

            int noOfCols = metaData.getColumnCount();
            String[] col = new String[noOfCols];
            String[] row = new String[noOfCols];

            for (int i = 0; i < noOfCols; i++) {
                col[i] = metaData.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(col);

            tableModel.setRowCount(0);
            while(resultSet.next()) {
                for (int i = 0; i < noOfCols; i++) {
                    row[i] = resultSet.getString(i + 1);
                }

                tableModel.addRow(row);
            }

            connection.close();
            resultSet.close();
            statement.close();
        } 
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int insertRecord(String id, String name, String email, String phone) {
        int row = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, phone);

            row = statement.executeUpdate();

            connection.close();
            statement.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }

        return row;
    }
 
    public static int deleteRecord(String id) {
        int row = 0;

        try {
            Connection connection = getConnection();
            
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setString(1, id);

            row = statement.executeUpdate();

            statement.close();
            connection.close();
        } 
        
        catch (SQLException e) {
            System.out.println(e);
        }

        return row;
    }

    public static int updateRecord(String id, String name, String email, String phone) {
        int row = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, id);

            row = statement.executeUpdate();

            connection.close();
            statement.close();
        } 
        catch (SQLException e) {
            System.out.println(e);
        }

        return row;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
