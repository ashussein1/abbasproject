package abbas.project.hotel.controller;

import abbas.project.hotel.service.FeedbackService;
import abbas.project.hotel.service.ReservationService;
import abbas.project.hotel.repository.RoomRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.google.inject.Inject;

public class AdminDashboardController {

    private final RoomRepository roomRepository;
    private final ReservationService reservationService;
    private final FeedbackService feedbackService;

    @FXML
    private Label totalRoomsLabel;

    @FXML
    private Label occupiedTodayLabel;

    @FXML
    private Label newFeedbackLabel;

    @Inject
    public AdminDashboardController(RoomRepository roomRepository,
                                    ReservationService reservationService,
                                    FeedbackService feedbackService) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
        this.feedbackService = feedbackService;
    }

    @FXML
    private void initialize() {
        // Simple metrics
        long totalRooms = roomRepository.count();
        long occupied = reservationService.countActiveReservations();
        long feedback = feedbackService.count();

        totalRoomsLabel.setText(String.valueOf(totalRooms));
        occupiedTodayLabel.setText(String.valueOf(occupied));
        newFeedbackLabel.setText(String.valueOf(feedback));
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
