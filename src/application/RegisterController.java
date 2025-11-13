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
import java.time.LocalDate;

public class RegisterController {

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
    private VBox formContainer;

    @FXML
    private Button registerButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;

    private DonorDAO donorDAO = new DonorDAO();

    @FXML
    private void initialize() {
        // Add advanced animations to the form
        if (formContainer != null) {
            // Fade in animation
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), formContainer);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            
            // Slide in animation
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), formContainer);
            translateTransition.setFromY(100);
            translateTransition.setToY(0);
            
            // Sequential transition
            SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition, translateTransition);
            sequentialTransition.play();
        }

        // Add advanced hover effects to buttons
        addAdvancedHoverEffects(registerButton, "#27ae60");
        addAdvancedHoverEffects(clearButton, "#f39c12");
        addAdvancedHoverEffects(backButton, "#34495e");
        
        // Add focus effects to text fields
        addFocusEffects(nameField);
        addFocusEffects(bloodGroupField);
        addFocusEffects(cityField);
        addFocusEffects(contactField);
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
    
    private void addFocusEffects(TextField textField) {
        if (textField != null) {
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    // Add glow when focused
                    Glow glow = new Glow(0.3);
                    textField.setEffect(glow);
                    
                    // Add shadow
                    DropShadow shadow = new DropShadow();
                    shadow.setRadius(15);
                    shadow.setOffsetX(0);
                    shadow.setOffsetY(0);
                    shadow.setColor(Color.web("#3498db"));
                    textField.setEffect(shadow);
                } else {
                    // Remove effects when not focused
                    textField.setEffect(null);
                }
            });
        }
    }

    @FXML
    private void handleRegisterButton() {
        if (nameField == null || bloodGroupField == null || cityField == null || 
            contactField == null || availabilityCheckBox == null || lastDonationDatePicker == null) return;

        String name = nameField.getText();
        String bloodGroup = bloodGroupField.getText();
        String city = cityField.getText();
        String contact = contactField.getText();
        boolean availability = availabilityCheckBox.isSelected();
        LocalDate lastDonationDate = lastDonationDatePicker.getValue();

        // Validate input
        if (name.isEmpty() || bloodGroup.isEmpty() || city.isEmpty() || contact.isEmpty() || lastDonationDate == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
            // Add shake animation to indicate error
            addErrorAnimation(formContainer);
            return;
        }

        Donor donor = new Donor();
        donor.setName(name);
        donor.setBloodGroup(bloodGroup);
        donor.setCity(city);
        donor.setContact(contact);
        donor.setAvailability(availability);
        donor.setLastDonationDate(lastDonationDate);

        try {
            if (donorDAO.registerDonor(donor)) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Success!", "Donor registered successfully!");
                clearFields();
                // Add success animation
                addSuccessAnimation(formContainer);
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed!", "Failed to register donor.");
                addErrorAnimation(formContainer);
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error!", "Failed to register donor: " + e.getMessage());
            e.printStackTrace();
            addErrorAnimation(formContainer);
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
    private void handleClearButton() {
        clearFields();
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
        }
    }

    private void clearFields() {
        if (nameField != null) nameField.clear();
        if (bloodGroupField != null) bloodGroupField.clear();
        if (cityField != null) cityField.clear();
        if (contactField != null) contactField.clear();
        if (availabilityCheckBox != null) availabilityCheckBox.setSelected(true);
        if (lastDonationDatePicker != null) lastDonationDatePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}