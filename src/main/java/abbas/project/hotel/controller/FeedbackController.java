package abbas.project.hotel.controller;

import abbas.project.hotel.service.FeedbackService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FeedbackController {

    private final FeedbackService feedbackService;

    @FXML
    private Spinner<Integer> ratingSpinner;

    @FXML
    private TextArea commentsArea;

    @FXML
    private Label statusLabel;

    @Inject
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @FXML
    private void initialize() {
        ratingSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 5));
    }

    @FXML
    private void handleSubmit() {
        int rating = ratingSpinner.getValue();
        String comments = commentsArea.getText() == null ? "" : commentsArea.getText().trim();

        feedbackService.submitAnonymousFeedback(rating, comments);

        statusLabel.getStyleClass().removeAll("error", "success");
        statusLabel.getStyleClass().add("success");
        statusLabel.setText("Thank you for your feedback! (Rating: " + rating + ")");
        commentsArea.clear();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
