package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmergencyController {

    @FXML
    private TextField bloodGroupField;

    @FXML
    private TableView<Donor> donorTable;

    @FXML
    private TableColumn<Donor, Integer> idColumn;

    @FXML
    private TableColumn<Donor, String> nameColumn;

    @FXML
    private TableColumn<Donor, String> bloodGroupColumn;

    @FXML
    private TableColumn<Donor, String> cityColumn;

    @FXML
    private TableColumn<Donor, String> contactColumn;

    @FXML
    private TableColumn<Donor, Boolean> availabilityColumn;

    @FXML
    private TableColumn<Donor, String> lastDonationColumn;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox emergencyContainer;

    private DonorDAO donorDAO = new DonorDAO();
    private ObservableList<Donor> donorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize table columns
        if (idColumn != null) idColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        if (nameColumn != null) nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (bloodGroupColumn != null) bloodGroupColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        if (cityColumn != null) cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        if (contactColumn != null) contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        if (availabilityColumn != null) availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        if (lastDonationColumn != null) lastDonationColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getLastDonationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
        );
        
        // Set the donor list to the table
        if (donorTable != null) donorTable.setItems(donorList);
        
        // Add animations to the emergency container
        if (emergencyContainer != null) {
            // Fade in animation
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), emergencyContainer);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            
            // Slide in animation
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), emergencyContainer);
            translateTransition.setFromY(100);
            translateTransition.setToY(0);
            
            // Sequential transition
            SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition, translateTransition);
            sequentialTransition.play();
        }

        // Add advanced hover effects to buttons
        addAdvancedHoverEffects(searchButton, "#e74c3c");
        addAdvancedHoverEffects(backButton, "#34495e");
        
        // Add pulse animation to search button to indicate urgency
        addPulseAnimation(searchButton);
        
        // Add row selection effect to table
        addTableRowEffects();
    }

    private void addAdvancedHoverEffects(Button button, String baseColor) {
        if (button != null) {
            button.setOnMouseEntered(e -> {
                // Scale transition
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
                scaleTransition.setToX(1.05);
                scaleTransition.setToY(1.05);
                
                // Glow effect
                Glow glow = new Glow(0.4);
                button.setEffect(glow);
                
                // Shadow effect
                DropShadow shadow = new DropShadow();
                shadow.setRadius(20);
                shadow.setOffsetX(0);
                shadow.setOffsetY(0);
                shadow.setColor(Color.web(baseColor));
                button.setEffect(shadow);
                
                scaleTransition.play();
            });

            button.setOnMouseExited(e -> {
                // Scale transition back
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                
                // Remove effects
                button.setEffect(null);
                
                scaleTransition.play();
            });
        }
    }
    
    private void addPulseAnimation(Button button) {
        if (button != null) {
            ScaleTransition pulseTransition = new ScaleTransition(Duration.seconds(1.5), button);
            pulseTransition.setFromX(1.0);
            pulseTransition.setFromY(1.0);
            pulseTransition.setToX(1.02);
            pulseTransition.setToY(1.02);
            pulseTransition.setAutoReverse(true);
            pulseTransition.setCycleCount(ScaleTransition.INDEFINITE);
            pulseTransition.play();
        }
    }
    
    private void addTableRowEffects() {
        if (donorTable != null) {
            donorTable.setRowFactory(tv -> {
                TableRow<Donor> row = new TableRow<>();
                row.setOnMouseEntered(event -> {
                    if (!row.isEmpty()) {
                        Glow glow = new Glow(0.2);
                        row.setEffect(glow);
                    }
                });
                row.setOnMouseExited(event -> {
                    row.setEffect(null);
                });
                return row;
            });
        }
    }

    @FXML
    private void handleSearchButton() {
        if (bloodGroupField == null) return;
        
        String bloodGroup = bloodGroupField.getText();
        if (bloodGroup.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter a blood group");
            addErrorAnimation(emergencyContainer);
            return;
        }

        try {
            // Use the correct method name
            List<Donor> donors = donorDAO.getAvailableDonorsByBloodGroup(bloodGroup);
            if (donorList != null) {
                donorList.clear();
                donorList.addAll(donors);
            }
            
            if (donors.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No available donors found with blood group: " + bloodGroup);
                addWarningAnimation(emergencyContainer);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Found " + donors.size() + " available donors!");
                addSuccessAnimation(emergencyContainer);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to search donors: " + e.getMessage());
            e.printStackTrace();
            addErrorAnimation(emergencyContainer);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            addErrorAnimation(emergencyContainer);
        }
    }

    private void addErrorAnimation(VBox container) {
        if (container != null) {
            TranslateTransition shake = new TranslateTransition(Duration.millis(100), container);
            shake.setByX(10);
            shake.setCycleCount(4);
            shake.setAutoReverse(true);
            shake.play();
        }
    }
    
    private void addWarningAnimation(VBox container) {
        if (container != null) {
            ScaleTransition scale = new ScaleTransition(Duration.millis(300), container);
            scale.setToX(1.01);
            scale.setToY(1.01);
            scale.setCycleCount(2);
            scale.setAutoReverse(true);
            scale.play();
        }
    }
    
    private void addSuccessAnimation(VBox container) {
        if (container != null) {
            ScaleTransition scale = new ScaleTransition(Duration.millis(500), container);
            scale.setToX(1.02);
            scale.setToY(1.02);
            scale.setCycleCount(2);
            scale.setAutoReverse(true);
            scale.play();
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            if (backButton != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 1000, 800);
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate back to main menu: " + e.getMessage());
            addErrorAnimation(emergencyContainer);
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