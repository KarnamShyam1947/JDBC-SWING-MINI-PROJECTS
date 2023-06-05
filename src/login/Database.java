package login;

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

    public static final String INSERT_QUERY = "INSERT INTO users VALUES(?, ?)";
    public static final String SEARCH_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String COUNT_QUERY = "SELECT COUNT(*) FROM users";
    public static final String USERNAME_QUERY = "SELECT username FROM users";
    
    public static Connection getConnection() {
        Connection connection = null;
        
        try {
            Class.forName(DRIVER_PATH);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } 

        catch (ClassNotFoundException e) {
            System.out.println("Failed to load the mysql driver");
            System.out.println(e);
        }
        
        catch (SQLException e) {
            System.out.println("Failed to establish the connection");
            System.out.println(e);
        }

        return connection;
    }

    public static int insertUser(String username, String password) {
        int row = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);

            statement.setString(1, username);
            statement.setString(2, password);

            row = statement.executeUpdate();

            statement.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return row;
    }

    public static boolean isValidUser(String username, String password) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SEARCH_QUERY);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                return true;
            }

            statement.close();
            connection.close();
        }

        catch(Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public static int getNoOfRecords() {
        int cnt = 0;

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(COUNT_QUERY);

            while(resultSet.next()) {
                cnt = resultSet.getInt(1);
                System.out.println("Count : " + cnt);
            }

            connection.close();
            statement.close();
            resultSet.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return cnt;
    }

    public static void updateUserNames(Vector<String> list) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERNAME_QUERY);

            list.removeAllElements();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}
