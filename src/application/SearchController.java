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

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> searchTypeComboBox;

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
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox searchContainer;

    private DonorDAO donorDAO = new DonorDAO();
    private ObservableList<Donor> donorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize search type combo box
        if (searchTypeComboBox != null) {
            searchTypeComboBox.setItems(FXCollections.observableArrayList(
                "Blood Group", "Name", "City", "Contact"
            ));
            searchTypeComboBox.setValue("Blood Group");
        }
        
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
        
        // Add animations to the search container
        if (searchContainer != null) {
            // Fade in animation
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), searchContainer);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            
            // Slide in animation
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), searchContainer);
            translateTransition.setFromY(100);
            translateTransition.setToY(0);
            
            // Sequential transition
            SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition, translateTransition);
            sequentialTransition.play();
        }

        // Add advanced hover effects to buttons
        addAdvancedHoverEffects(searchButton, "#3498db");
        addAdvancedHoverEffects(updateButton, "#f39c12");
        addAdvancedHoverEffects(deleteButton, "#e74c3c");
        addAdvancedHoverEffects(backButton, "#34495e");
        
        // Add row selection effect to table
        addTableRowEffects();
        
        // Load all donors by default
        handleSearchButton();
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
        if (searchField == null || searchTypeComboBox == null) return;
        
        String searchTerm = searchField.getText();
        String searchType = searchTypeComboBox.getValue();
        
        if (searchTerm.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter a search term");
            addErrorAnimation(searchContainer);
            return;
        }

        try {
            List<Donor> donors = null;
            
            switch (searchType) {
                case "Blood Group":
                    donors = donorDAO.searchDonorsByBloodGroup(searchTerm);
                    break;
                case "Name":
                    donors = donorDAO.searchDonorsByName(searchTerm);
                    break;
                case "City":
                    donors = donorDAO.filterDonorsByCity(searchTerm);
                    break;
                case "Contact":
                    donors = donorDAO.searchDonorsByContact(searchTerm);
                    break;
                default:
                    donors = donorDAO.searchDonorsByBloodGroup(searchTerm);
                    break;
            }
            
            if (donorList != null) {
                donorList.clear();
                donorList.addAll(donors);
            }
            
            if (donors.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No donors found matching your search criteria.");
                addWarningAnimation(searchContainer);
            } else {
                addSuccessAnimation(searchContainer);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to search donors: " + e.getMessage());
            e.printStackTrace();
            addErrorAnimation(searchContainer);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            addErrorAnimation(searchContainer);
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
    private void handleUpdateButton() {
        if (donorTable == null) return;
        
        Donor selectedDonor = donorTable.getSelectionModel().getSelectedItem();
        if (selectedDonor == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a donor to update.");
            addWarningAnimation(searchContainer);
            return;
        }

        try {
            // Load the update screen with the selected donor data
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UpdateDonor.fxml"));
            Parent root = loader.load();
            
            UpdateController updateController = loader.getController();
            updateController.setDonorData(selectedDonor);
            
            // Get the current stage from the donorTable
            Stage stage = (Stage) donorTable.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load update screen: " + e.getMessage());
            e.printStackTrace();
            addErrorAnimation(searchContainer);
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (donorTable == null) return;
        
        Donor selectedDonor = donorTable.getSelectionModel().getSelectedItem();
        if (selectedDonor == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a donor to delete.");
            addWarningAnimation(searchContainer);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Are you sure you want to delete donor " + selectedDonor.getName() + "?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (donorDAO.deleteDonor(selectedDonor.getDonorId())) {
                        if (donorList != null) donorList.remove(selectedDonor);
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Donor deleted successfully!");
                        addSuccessAnimation(searchContainer);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete donor.");
                        addErrorAnimation(searchContainer);
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete donor: " + e.getMessage());
                    e.printStackTrace();
                    addErrorAnimation(searchContainer);
                }
            }
        });
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
            addErrorAnimation(searchContainer);
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