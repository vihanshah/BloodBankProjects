package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.time.LocalDate;

public class UpdateController {

    @FXML
    private TextField donorIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField bloodGroupField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField contactField;

    @FXML
    private CheckBox availabilityCheckBox;

    @FXML
    private DatePicker lastDonationDatePicker;

    @FXML
    private Button loadButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;

    private DonorDAO donorDAO = new DonorDAO();
    private Donor currentDonor = null;

    @FXML
    private void initialize() {
        // Style buttons
        styleButton(loadButton, "#3498db");
        styleButton(updateButton, "#27ae60");
        styleButton(clearButton, "#f39c12");
        styleButton(backButton, "#34495e");
        
        // Style text fields
        styleTextField(donorIdField);
        styleTextField(nameField);
        styleTextField(bloodGroupField);
        styleTextField(cityField);
        styleTextField(contactField);
        
        // Style date picker
        if (lastDonationDatePicker != null) {
            lastDonationDatePicker.setPrefHeight(40);
        }
    }

    private void styleButton(Button button, String color) {
        if (button != null) {
            button.setBackground(new Background(new BackgroundFill(Color.web(color), new CornerRadii(20), Insets.EMPTY)));
            button.setTextFill(Color.WHITE);
            button.setOnMouseEntered(e -> button.setBackground(new Background(new BackgroundFill(Color.web(darkenColor(color)), new CornerRadii(20), Insets.EMPTY))));
            button.setOnMouseExited(e -> button.setBackground(new Background(new BackgroundFill(Color.web(color), new CornerRadii(20), Insets.EMPTY))));
        }
    }

    private void styleTextField(TextField textField) {
        if (textField != null) {
            textField.setPrefHeight(40);
        }
    }

    private String darkenColor(String hexColor) {
        // Simple darkening by reducing brightness
        Color color = Color.web(hexColor);
        return Color.color(
            Math.max(0, color.getRed() - 0.1),
            Math.max(0, color.getGreen() - 0.1),
            Math.max(0, color.getBlue() - 0.1)
        ).toString();
    }

    @FXML
    private void handleLoadButton() {
        try {
            if (donorIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a donor ID");
                return;
            }
            
            int donorId = Integer.parseInt(donorIdField.getText());
            
            // Load the donor data from the database
            Donor donor = donorDAO.getDonorById(donorId);
            
            if (donor != null) {
                // Store the current donor
                currentDonor = donor;
                
                // Populate the fields with donor data
                nameField.setText(donor.getName());
                bloodGroupField.setText(donor.getBloodGroup());
                cityField.setText(donor.getCity());
                contactField.setText(donor.getContact());
                availabilityCheckBox.setSelected(donor.isAvailability());
                lastDonationDatePicker.setValue(donor.getLastDonationDate());
                
                showAlert(Alert.AlertType.INFORMATION, "Success", "Donor data loaded successfully");
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No donor found with ID: " + donorId);
                clearFields();
                currentDonor = null;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid donor ID");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "Failed to load donor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateButton() {
        try {
            // If we haven't loaded a donor yet, try to load it first
            if (currentDonor == null && !donorIdField.getText().isEmpty()) {
                handleLoadButton();
                // If loading failed, return
                if (currentDonor == null) {
                    return;
                }
            }
            
            // If we still don't have a donor, show an error
            if (currentDonor == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please load a donor first");
                return;
            }
            
            String name = nameField.getText();
            String bloodGroup = bloodGroupField.getText();
            String city = cityField.getText();
            String contact = contactField.getText();
            boolean availability = availabilityCheckBox.isSelected();
            LocalDate lastDonationDate = lastDonationDatePicker.getValue();

            // Validate input
            if (name.isEmpty() || bloodGroup.isEmpty() || city.isEmpty() || contact.isEmpty() || lastDonationDate == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
                return;
            }

            // Update the current donor with new values
            currentDonor.setName(name);
            currentDonor.setBloodGroup(bloodGroup);
            currentDonor.setCity(city);
            currentDonor.setContact(contact);
            currentDonor.setAvailability(availability);
            currentDonor.setLastDonationDate(lastDonationDate);
            
            if (donorDAO.updateDonor(currentDonor)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Donor updated successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update donor");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            // Navigate back to the main menu
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root, 800, 600);
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate back to main menu: " + e.getMessage());
        }
    }

    @FXML
    private void clearFields() {
        nameField.clear();
        bloodGroupField.clear();
        cityField.clear();
        contactField.clear();
        availabilityCheckBox.setSelected(false);
        lastDonationDatePicker.setValue(null);
    }

    public void setDonorData(Donor donor) {
        if (donor != null) {
            currentDonor = donor;
            donorIdField.setText(String.valueOf(donor.getDonorId()));
            nameField.setText(donor.getName());
            bloodGroupField.setText(donor.getBloodGroup());
            cityField.setText(donor.getCity());
            contactField.setText(donor.getContact());
            availabilityCheckBox.setSelected(donor.isAvailability());
            lastDonationDatePicker.setValue(donor.getLastDonationDate());
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