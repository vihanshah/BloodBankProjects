package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;

public class FilterController {

    @FXML
    private TextField cityFilterField;

    @FXML
    private ComboBox<String> bloodGroupComboBox;

    @FXML
    private CheckBox availabilityCheckBox;

    @FXML
    private TableView<Donor> donorTableView;

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
    private Button applyFilterButton;

    @FXML
    private Button clearFilterButton;

    @FXML
    private Button backButton;

    private DonorDAO donorDAO = new DonorDAO();
    private ObservableList<Donor> donorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bloodGroupColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Initialize blood group combo box
        bloodGroupComboBox.setItems(FXCollections.observableArrayList(
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        ));

        // Load all donors initially
        loadDonorData();
    }

    @FXML
    private void handleApplyFilterButton() {
        try {
            String city = cityFilterField.getText();
            String bloodGroup = bloodGroupComboBox.getValue();
            boolean availability = availabilityCheckBox.isSelected();

            List<Donor> filteredDonors = donorDAO.getAllDonors(); // Start with all donors

            // Apply city filter if specified
            if (city != null && !city.isEmpty()) {
                filteredDonors = filteredDonors.stream()
                        .filter(donor -> donor.getCity().equalsIgnoreCase(city))
                        .collect(java.util.stream.Collectors.toList());
            }

            // Apply blood group filter if specified
            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                filteredDonors = filteredDonors.stream()
                        .filter(donor -> donor.getBloodGroup().equalsIgnoreCase(bloodGroup))
                        .collect(java.util.stream.Collectors.toList());
            }

            // Apply availability filter
            filteredDonors = filteredDonors.stream()
                    .filter(donor -> donor.isAvailability() == availability)
                    .collect(java.util.stream.Collectors.toList());

            donorList.setAll(filteredDonors);
            donorTableView.setItems(donorList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Filter Error", "An error occurred while applying filters: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearFilterButton() {
        cityFilterField.clear();
        bloodGroupComboBox.setValue(null);
        availabilityCheckBox.setSelected(false);
        loadDonorData();
    }

    @FXML
    private void handleBackButton() {
        try {
            // Navigate back to the main menu
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate back to main menu: " + e.getMessage());
        }
    }

    private void loadDonorData() {
        try {
            List<Donor> donors = donorDAO.getAllDonors();
            if (donors != null) {
                donorList.setAll(donors);
                donorTableView.setItems(donorList);
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load donor data from database");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "An error occurred while loading donor data: " + e.getMessage());
            e.printStackTrace();
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