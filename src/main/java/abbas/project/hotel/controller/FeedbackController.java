package abbas.project.hotel.controller;

import abbas.project.hotel.service.FeedbackService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import com.google.inject.Inject;

public class FeedbackController {

    private final FeedbackService feedbackService;

    @FXML
    private Spinner<Integer> ratingSpinner;

    @FXML
    private TextArea commentsArea;

    @FXML
    private TextField reservationIdField;

    @FXML
    private Label statusLabel;

    @Inject
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @FXML
    private void initialize() {
        ratingSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 5));
    }

    @FXML
    private void handleSubmit() {
        String resIdText = reservationIdField.getText() == null ? "" : reservationIdField.getText().trim();
        if (resIdText.isEmpty()) {
            statusLabel.setText("Please enter your reservation ID.");
            statusLabel.getStyleClass().setAll("status-label", "error");
            return;
        }

        long resId = Long.parseLong(resIdText);
        int rating = ratingSpinner.getValue();
        String comments = commentsArea.getText() == null ? "" : commentsArea.getText().trim();

        try {
            feedbackService.submitFeedback(resId, rating, comments);
            statusLabel.getStyleClass().setAll("status-label", "success");
            statusLabel.setText("Thank you for your feedback!");
            commentsArea.clear();
            reservationIdField.clear();
        } catch (Exception e) {
            statusLabel.getStyleClass().setAll("status-label", "error");
            statusLabel.setText("Unable to submit feedback. Make sure the reservation exists.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
