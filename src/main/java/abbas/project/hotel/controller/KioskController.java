package abbas.project.hotel.controller;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.service.ReservationService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class KioskController {

    private final ReservationService reservationService;

    @FXML
    private TextField adultsField;

    @FXML
    private TextField childrenField;

    @FXML
    private DatePicker checkInPicker;

    @FXML
    private DatePicker checkOutPicker;

    @FXML
    private Label statusLabel;

    @Inject
    public KioskController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @FXML
    private void handleContinue() {
        String adultsText = adultsField.getText() == null ? "" : adultsField.getText().trim();
        String childrenText = childrenField.getText() == null ? "" : childrenField.getText().trim();
        LocalDate in = checkInPicker.getValue();
        LocalDate out = checkOutPicker.getValue();

        statusLabel.getStyleClass().removeAll("error", "success");

        if (adultsText.isEmpty() || in == null || out == null) {
            statusLabel.setText("Please fill in adults and both dates.");
            statusLabel.getStyleClass().add("error");
            return;
        }

        int adults;
        int children = 0;
        try {
            adults = Integer.parseInt(adultsText);
            if (!childrenText.isEmpty()) {
                children = Integer.parseInt(childrenText);
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Adults / children must be numbers.");
            statusLabel.getStyleClass().add("error");
            return;
        }

        Reservation reservation = reservationService.createKioskReservation(
                adults, children, in, out
        );

        statusLabel.setText(
                "Booking captured for room "
                        + (reservation.getRoom() != null ? reservation.getRoom().getRoomNumber() : "?")
                        + " from " + in + " to " + out + "."
        );
        statusLabel.getStyleClass().add("success");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // launcher window is still open behind
    }
}
