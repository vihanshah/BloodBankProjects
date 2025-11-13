package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Launching Blood Bank Database application...");
            
            // Initialize the database
            try {
                DonorDAO.initializeDatabase();
                System.out.println("Database initialized successfully!");
                
                // Add sample data
                DonorDAO.addSampleData();
            } catch (SQLException e) {
                System.err.println("Failed to initialize database: " + e.getMessage());
                e.printStackTrace();
            }
            
            System.out.println("Loading FXML...");
            Parent root = FXMLLoader.load(getClass().getResource("/resources/MainMenu.fxml"));
            System.out.println("FXML loaded successfully!");
            
            Scene scene = new Scene(root, 1000, 800);
            primaryStage.setTitle("Blood Bank Management System");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            System.out.println("Showing stage...");
            primaryStage.show();
            System.out.println("Stage shown!");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}