package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import java.awt.Desktop;
import java.net.URI;

public class AboutController {

    @FXML
    private Button backButton;

    @FXML
    private Hyperlink githubLink;

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
        }
    }

    @FXML
    private void handleGithubLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}