package contacts;

import java.sql.*;
import java.util.Vector;

public class Database {
    // public static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    // public static final String URL = "jdbc:mysql://localhost:3306/test";
    // public static final String USER = "root";
    // public static final String PASSWORD = "";

    public static final String DRIVER_PATH = "oracle.jdbc.driver.OracleDriver";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String USER = "system";
    public static final String PASSWORD = "admin";

    public static final String INSERT_QUERY = "INSERT INTO contacts VALUES(?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM contacts WHERE name = ?";
    public static final String COUNT_QUERY = "SELECT COUNT(*) FROM contacts";
    public static final String NUMBER_QUERY = "SELECT numbers FROM contacts";
    public static final String NAME_QUERY = "SELECT name FROM contacts";
    
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

    public static int insertContact(String name, String number) {
        int row = 0;

        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);

            statement.setString(1, name);
            statement.setString(2, number);

            row = statement.executeUpdate();

            connection.close();
            statement.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return row;
    }

    public static int deleteContact(String name) {
        int row = 0;

        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

            statement.setString(1, name);

            row = statement.executeUpdate();

            connection.close();
            statement.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return row;
    }

    public static Vector<String> getDetails() {
        Vector<String> finalNames = null;
        Vector<String> names = null;
        Vector<String> numbers = null;

        try {
            names = new Vector<>();
            numbers = new Vector<>();
            finalNames = new Vector<>();

            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet namesResultSet = statement.executeQuery(NAME_QUERY);
            while(namesResultSet.next()) {
                names.add(namesResultSet.getString(1));
            }
            
            ResultSet numbersResultSet = statement.executeQuery(NUMBER_QUERY);
            while(numbersResultSet.next()) {
                numbers.add(numbersResultSet.getString(1));
            }

            for (int i = 0; i < names.size(); i++) {
                finalNames.add(String.format("%d. %s - %s", (i+1), names.get(i), numbers.get(i)));
            }

            statement.close();
            connection.close();
            namesResultSet.close();
            numbersResultSet.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }

        return finalNames;
    }

    public static int getCount() {
        int count = 0;

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_QUERY);

            while(resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return count;
    }

}
