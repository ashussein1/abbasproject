package abbas.project.hotel.controller;

import abbas.project.hotel.app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ModeLauncherController {

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlPath));
            loader.setControllerFactory(MainApp.getInjector()::getInstance);

            Parent root = loader.load();
            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass()
                            .getResource("/abbas/project/hotel/style/dark-theme.css")
                            .toExternalForm()
            );

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openKiosk() {
        openWindow("/abbas/project/hotel/view/kiosk-view.fxml",
                "Hotel Reservation System - Kiosk");
    }

    @FXML
    private void openAdmin() {
        openWindow("/abbas/project/hotel/view/admin-login-view.fxml",
                "Hotel Reservation System - Admin Login");
    }

    @FXML
    private void openFeedback() {
        openWindow("/abbas/project/hotel/view/feedback-view.fxml",
                "Hotel Reservation System - Feedback");
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
