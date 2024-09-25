package expense_tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static final String URL = "jdbc:mysql://localhost:3306/expensetracker";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Swathika@2003"; 

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

