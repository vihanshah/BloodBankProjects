package application;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class StatisticsController {

    @FXML
    private PieChart bloodGroupChart;

    @FXML
    private Label totalDonorsLabel;

    @FXML
    private Button backButton;

    private DonorDAO donorDAO = new DonorDAO();

    @FXML
    private void initialize() {
        // Style button
        styleButton(backButton, "#34495e");
        
        // Load statistics
        loadStatistics();
    }

    private void styleButton(Button button, String color) {
        if (button != null) {
            button.setBackground(new Background(new BackgroundFill(Color.web(color), new CornerRadii(20), Insets.EMPTY)));
            button.setTextFill(Color.WHITE);
            button.setOnMouseEntered(e -> button.setBackground(new Background(new BackgroundFill(Color.web(darkenColor(color)), new CornerRadii(20), Insets.EMPTY))));
            button.setOnMouseExited(e -> button.setBackground(new Background(new BackgroundFill(Color.web(color), new CornerRadii(20), Insets.EMPTY))));
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

    private void loadStatistics() {
        try {
            // Get all donors
            List<Donor> donors = donorDAO.getAllDonors();
            
            // Calculate total donors
            int totalDonors = donors.size();
            totalDonorsLabel.setText("Total Donors: " + totalDonors);
            
            // Count blood groups
            Map<String, Integer> bloodGroupCount = new HashMap<>();
            for (Donor donor : donors) {
                String bloodGroup = donor.getBloodGroup();
                bloodGroupCount.put(bloodGroup, bloodGroupCount.getOrDefault(bloodGroup, 0) + 1);
            }
            
            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : bloodGroupCount.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            
            bloodGroupChart.setData(pieChartData);
            
            // Style the chart
            bloodGroupChart.setPrefSize(700, 450);
            bloodGroupChart.setTitle("Blood Group Distribution");
            bloodGroupChart.setTitleSide(javafx.geometry.Side.TOP);
            
            // Add hover effect to show counts
            for (PieChart.Data data : bloodGroupChart.getData()) {
                data.getNode().setOnMouseEntered(event -> {
                    totalDonorsLabel.setText("Blood Group: " + data.getName() + ", Count: " + (int) data.getPieValue());
                });
                
                data.getNode().setOnMouseExited(event -> {
                    totalDonorsLabel.setText("Total Donors: " + totalDonors);
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            totalDonorsLabel.setText("Error loading statistics");
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
        }
    }
}