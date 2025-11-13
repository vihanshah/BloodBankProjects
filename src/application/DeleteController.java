package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;

public class DeleteController {

    @FXML
    private TextField donorIdField;

    @FXML
    private Button backButton;

    private DonorDAO donorDAO = new DonorDAO();

    @FXML
    private void handleDeleteButton() {
        try {
            String donorIdText = donorIdField.getText();
            if (donorIdText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a donor ID");
                return;
            }

            int donorId = Integer.parseInt(donorIdText);

            // Confirm deletion
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Delete");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to delete donor with ID: " + donorId + "?");
            
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        if (donorDAO.deleteDonor(donorId)) {
                            showAlert(Alert.AlertType.INFORMATION, "Success", "Donor deleted successfully!");
                            donorIdField.clear();
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete donor.");
                        }
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete donor: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid donor ID");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearButton() {
        donorIdField.clear();
    }

    @FXML
    private void handleBackButton() {
        try {
            if (backButton != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) backButton.getScene().getWindow();
                Scene scene = new Scene(root, 1000, 800);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate back to main menu: " + e.getMessage());
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