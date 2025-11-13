package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Label titleLabel;

    @FXML
    private VBox buttonContainer;

    @FXML
    private Button handleRegisterButton;

    @FXML
    private Button handleSearchButton;

    @FXML
    private Button handleUpdateButton;

    @FXML
    private Button handleDeleteButton;

    @FXML
    private Button handleEmergencyButton;

    @FXML
    private Button handleFilterButton;

    @FXML
    private Button handleStatisticsButton;

    @FXML
    private Button handleAboutButton;

    @FXML
    private void initialize() {
        // Add advanced animations to the title
        if (titleLabel != null) {
            // Fade in animation
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), titleLabel);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            
            // Scale animation
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), titleLabel);
            scaleTransition.setFromX(0.5);
            scaleTransition.setFromY(0.5);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            
            // Parallel transition
            ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
            parallelTransition.play();
        }

        // Add advanced animations to buttons
        addButtonAnimations(handleRegisterButton);
        addButtonAnimations(handleSearchButton);
        addButtonAnimations(handleUpdateButton);
        addButtonAnimations(handleDeleteButton);
        addButtonAnimations(handleEmergencyButton);
        addButtonAnimations(handleFilterButton);
        addButtonAnimations(handleStatisticsButton);
        addButtonAnimations(handleAboutButton);
        
        // Add glow effect to emergency button
        if (handleEmergencyButton != null) {
            Glow glow = new Glow(0.3);
            handleEmergencyButton.setEffect(glow);
        }
    }

    private void addButtonAnimations(Button button) {
        if (button != null) {
            // Advanced hover effects
            button.setOnMouseEntered(e -> {
                // Scale transition
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
                scaleTransition.setToX(1.05);
                scaleTransition.setToY(1.05);
                
                // Glow effect
                Glow glow = new Glow(0.5);
                button.setEffect(glow);
                
                // Shadow effect
                DropShadow shadow = new DropShadow();
                shadow.setRadius(15);
                shadow.setOffsetX(0);
                shadow.setOffsetY(0);
                shadow.setColor(Color.web("#1abc9c"));
                button.setEffect(shadow);
                
                scaleTransition.play();
            });

            button.setOnMouseExited(e -> {
                // Scale transition back
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                
                // Remove glow
                if (button == handleEmergencyButton) {
                    Glow glow = new Glow(0.3);
                    button.setEffect(glow);
                } else {
                    button.setEffect(null);
                }
                
                scaleTransition.play();
            });
            
            // Add pulse animation for emergency button
            if (button == handleEmergencyButton) {
                addPulseAnimation(button);
            }
        }
    }
    
    private void addPulseAnimation(Button button) {
        if (button != null) {
            ScaleTransition pulseTransition = new ScaleTransition(Duration.seconds(2), button);
            pulseTransition.setFromX(1.0);
            pulseTransition.setFromY(1.0);
            pulseTransition.setToX(1.05);
            pulseTransition.setToY(1.05);
            pulseTransition.setAutoReverse(true);
            pulseTransition.setCycleCount(ScaleTransition.INDEFINITE);
            pulseTransition.play();
        }
    }

    @FXML
    private void handleRegisterButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/RegisterDonor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleRegisterButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load register donor screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/SearchDonor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleSearchButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load search donor screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UpdateDonor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleUpdateButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load update donor screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/DeleteDonor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleDeleteButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load delete donor screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleEmergencyButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/EmergencyMode.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleEmergencyButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load emergency mode screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleFilterButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/FilterDonors.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleFilterButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load filter donors screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleStatisticsButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Statistics.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleStatisticsButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load statistics screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleAboutButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/About.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) handleAboutButton.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load about screen: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}