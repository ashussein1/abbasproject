package abbas.project.hotel.controller;

import abbas.project.hotel.model.Feedback;
import abbas.project.hotel.service.FeedbackService;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ManageFeedbackController {

    private final FeedbackService feedbackService;
    private final ObservableList<Feedback> tableData = FXCollections.observableArrayList();

    @FXML private TableView<Feedback> feedbackTable;
    @FXML private TableColumn<Feedback, String> idColumn;
    @FXML private TableColumn<Feedback, String> guestColumn;
    @FXML private TableColumn<Feedback, String> ratingColumn;
    @FXML private TableColumn<Feedback, String> commentColumn;
    @FXML private TableColumn<Feedback, String> dateColumn;

    @Inject
    public ManageFeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @FXML
    private void initialize() {
        // 1. Setup Columns
        idColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(String.valueOf(cell.getValue().getId())));

        guestColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getGuest().getName()));

        ratingColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getRating() + " / 5"));

        commentColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getComment()));

        dateColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDate().toString()));

        // 2. Load Data
        loadData();
    }

    private void loadData() {
        tableData.setAll(feedbackService.getAllFeedback());
        feedbackTable.setItems(tableData);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}