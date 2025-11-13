package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/MainMenu.fxml"));
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("FXML Test");
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("FXML loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}