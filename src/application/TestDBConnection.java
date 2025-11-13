package application;

import java.sql.Connection;
import java.sql.SQLException;

import application.DBConnect;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            System.out.println("Testing database connection...");
            Connection conn = DBConnect.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("SUCCESS: Database connection established!");
                conn.close();
            } else {
                System.out.println("ERROR: Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}