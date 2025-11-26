package abbas.project.hotel.controller;

import abbas.project.hotel.app.MainApp;
import abbas.project.hotel.security.AdminAuthService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    private final AdminAuthService authService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Inject
    public AdminLoginController(AdminAuthService authService) {
        this.authService = authService;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        if (!authService.authenticate(username, password)) {
            errorLabel.setText("Invalid username or password.");
            return;
        }

        openAdminDashboard(event);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void openAdminDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/abbas/project/hotel/view/admin-dashboard-view.fxml")
            );
            // ✅ Use the same Guice injector as MainApp
            loader.setControllerFactory(MainApp.getInjector()::getInstance);

            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Hotel Reservation System - Admin Dashboard");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            errorLabel.setText("");

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Unable to open admin dashboard.");
        }
    }
}
