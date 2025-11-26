package abbas.project.hotel.controller;

import abbas.project.hotel.repository.RoomRepository;
import abbas.project.hotel.service.FeedbackService;
import abbas.project.hotel.service.ReservationService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdminDashboardController {

    private final ReservationService reservationService;
    private final RoomRepository roomRepository;
    private final FeedbackService feedbackService;

    @FXML
    private Label totalRoomsLabel;

    @FXML
    private Label occupiedTodayLabel;

    @FXML
    private Label newFeedbackLabel;

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
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
