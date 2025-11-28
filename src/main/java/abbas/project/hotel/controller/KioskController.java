package abbas.project.hotel.controller;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.service.ReservationService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class KioskController {

    private final ReservationService reservationService;

    @FXML private TextField adultsField;
    @FXML private TextField childrenField;
    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;
    @FXML private Label statusLabel;
    @FXML private Button waitlistButton;

    @Inject
    public KioskController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @FXML
    private void handleContinue() {
        if (!validateInputs()) return;

        int adults = Integer.parseInt(adultsField.getText().trim());
        int children = childrenField.getText().trim().isEmpty() ? 0 : Integer.parseInt(childrenField.getText().trim());
        LocalDate in = checkInPicker.getValue();
        LocalDate out = checkOutPicker.getValue();

        try {
            // 1. Create the reservation
            Reservation reservation = reservationService.createKioskReservation(adults, children, in, out);

            // 2. Calculate breakdown for the "Complete Estimate" requirement
            double subtotal = reservation.getTotalEstimate();
            double tax = subtotal * 0.13; // Assuming 13% tax
            double total = subtotal + tax;

            long nights = ChronoUnit.DAYS.between(in, out);
            if (nights < 1) nights = 1;

            // 3. Construct the detailed message
            String message = String.format(
                    "Success! Room %s Reserved.\n" +
                            "------------------------------------------------\n" +
                            "Nights: %d\n" +
                            "Rate: $%.2f / night\n" +
                            "Subtotal: $%.2f\n" +
                            "Tax (13%%): $%.2f\n" +
                            "------------------------------------------------\n" +
                            "TOTAL ESTIMATE: $%.2f\n\n" +
                            "NOTE: Billing will be handled at the front desk.",
                    reservation.getRoom().getRoomNumber(),
                    nights,
                    reservation.getRoom().getBasePrice(),
                    subtotal,
                    tax,
                    total
            );

            // 4. Show Confirmation Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking Confirmation");
            alert.setHeaderText("Reservation Confirmed");
            alert.setContentText(message);
            alert.showAndWait();

            // 5. Update status label
            statusLabel.setText("Booking Confirmed! Please proceed to front desk.");
            statusLabel.getStyleClass().removeAll("error");
            statusLabel.getStyleClass().add("success");

        } catch (IllegalStateException e) {
            statusLabel.setText(e.getMessage());
            statusLabel.getStyleClass().removeAll("success");
            statusLabel.getStyleClass().add("error");
            // Show waitlist button if room not found
            if (waitlistButton != null) waitlistButton.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("System error occurred.");
            statusLabel.getStyleClass().removeAll("success");
            statusLabel.getStyleClass().add("error");
        }
    }

    @FXML
    private void handleWaitlist() {
        if (!validateInputs()) return;

        int adults = Integer.parseInt(adultsField.getText().trim());
        int children = childrenField.getText().trim().isEmpty() ? 0 : Integer.parseInt(childrenField.getText().trim());
        LocalDate in = checkInPicker.getValue();
        LocalDate out = checkOutPicker.getValue();

        try {
            reservationService.joinWaitlist(adults, children, in, out);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Waitlist Joined");
            alert.setHeaderText(null);
            alert.setContentText("You have been added to the waitlist!\nWe will contact you if a room becomes available.");
            alert.showAndWait();

            statusLabel.setText("Added to waitlist.");
            statusLabel.getStyleClass().removeAll("error");
            statusLabel.getStyleClass().add("success");
        } catch (Exception e) {
            statusLabel.setText("Could not join waitlist.");
            statusLabel.getStyleClass().add("error");
        }
    }

    private boolean validateInputs() {
        String adultsText = adultsField.getText() == null ? "" : adultsField.getText().trim();
        statusLabel.getStyleClass().removeAll("error", "success");

        if (adultsText.isEmpty() || checkInPicker.getValue() == null || checkOutPicker.getValue() == null) {
            statusLabel.setText("Please fill in adults and dates.");
            statusLabel.getStyleClass().add("error");
            return false;
        }
        try {
            Integer.parseInt(adultsText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Adults must be a number.");
            statusLabel.getStyleClass().add("error");
            return false;
        }
        return true;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}