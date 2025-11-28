package abbas.project.hotel.controller;

import abbas.project.hotel.app.MainApp;
import abbas.project.hotel.repository.RoomRepository;
import abbas.project.hotel.service.FeedbackService;
import abbas.project.hotel.service.ReservationService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AdminDashboardController {

    private final ReservationService reservationService;
    private final RoomRepository roomRepository;
    private final FeedbackService feedbackService;

    @FXML private Label totalRoomsLabel;
    @FXML private Label occupiedTodayLabel;
    @FXML private Label newFeedbackLabel;

    @Inject
    public AdminDashboardController(ReservationService reservationService,
                                    RoomRepository roomRepository,
                                    FeedbackService feedbackService) {
        this.reservationService = reservationService;
        this.roomRepository = roomRepository;
        this.feedbackService = feedbackService;
    }

    @FXML
    private void initialize() {
        int totalRooms = roomRepository.findAll().size();
        int occupiedToday = reservationService.countReservationsOn(LocalDate.now());
        int feedbackCount = feedbackService.getAllFeedback().size();

        totalRoomsLabel.setText(String.valueOf(totalRooms));
        occupiedTodayLabel.setText(String.valueOf(occupiedToday));
        newFeedbackLabel.setText(String.valueOf(feedbackCount));
    }

    @FXML
    private void handleManageReservations(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/abbas/project/hotel/view/manage-reservations-view.fxml")
            );
            loader.setControllerFactory(MainApp.getInjector()::getInstance);

            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);

            Stage stage = new Stage();
            stage.setTitle("Manage Reservations");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load reservation management view.");
        }
    }

    @FXML
    private void handleFeedback() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/abbas/project/hotel/view/manage-feedback-view.fxml")
            );
            loader.setControllerFactory(MainApp.getInjector()::getInstance);

            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);

            Stage stage = new Stage();
            stage.setTitle("Manage Feedback");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load feedback view.");
        }
    }

    @FXML
    private void handleReports() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/abbas/project/hotel/view/activity-logs-view.fxml")
            );
            loader.setControllerFactory(MainApp.getInjector()::getInstance);

            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);

            Stage stage = new Stage();
            stage.setTitle("System Reports");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load reports view.");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}