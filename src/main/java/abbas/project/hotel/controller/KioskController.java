package abbas.project.hotel.controller;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.service.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import com.google.inject.Inject;
import java.time.LocalDate;
import java.util.Optional;

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

        int adults = adultsText.isEmpty() ? 0 : Integer.parseInt(adultsText);
        int children = childrenText.isEmpty() ? 0 : Integer.parseInt(childrenText);

        LocalDate in = checkInPicker.getValue();
        LocalDate out = checkOutPicker.getValue();

        statusLabel.getStyleClass().removeAll("error", "success");

        Optional<Reservation> result = reservationService.kioskQuickBook(
                "Kiosk Guest", adults, children, in, out);

        if (result.isEmpty()) {
            statusLabel.setText("Please check number of guests and dates.");
            statusLabel.getStyleClass().add("error");
            return;
        }

        Reservation r = result.get();
        statusLabel.setText(
                "Booking captured for room "
                        + r.getRoom().getRoomNumber()
                        + " from " + r.getCheckIn()
                        + " to " + r.getCheckOut()
                        + ". Total estimate: $" + String.format("%.2f", r.getTotalEstimate())
        );
        statusLabel.getStyleClass().add("success");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // launcher window is still open behind
    }
}
