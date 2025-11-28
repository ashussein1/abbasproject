package abbas.project.hotel.controller;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.model.ReservationStatus;
import abbas.project.hotel.service.ReservationService;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ManageReservationsController {

    private final ReservationService reservationService;
    private final ObservableList<Reservation> tableData = FXCollections.observableArrayList();

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, String> idColumn;
    @FXML private TableColumn<Reservation, String> guestColumn;
    @FXML private TableColumn<Reservation, String> roomColumn;
    @FXML private TableColumn<Reservation, String> datesColumn;
    @FXML private TableColumn<Reservation, String> statusColumn;
    @FXML private TableColumn<Reservation, String> totalColumn;

    @FXML private TextField searchField;
    @FXML private Label statusLabel;

    @Inject
    public ManageReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @FXML
    private void initialize() {
        // 1. Setup Columns (Extracting data from nested objects)
        idColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(String.valueOf(cell.getValue().getId())));

        guestColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getGuest().getName()));

        roomColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getRoom().getRoomNumber() + " (" + cell.getValue().getRoom().getType() + ")"));

        datesColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCheckIn() + " to " + cell.getValue().getCheckOut()));

        statusColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStatus().toString()));

        totalColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(String.format("$%.2f", cell.getValue().getTotalEstimate())));

        // 2. Load Data
        loadData();
    }

    private void loadData() {
        // Fetch fresh data from the database
        tableData.setAll(reservationService.getAllReservations());
        reservationTable.setItems(tableData);
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase().trim();

        if (query.isEmpty()) {
            reservationTable.setItems(tableData);
            return;
        }

        ObservableList<Reservation> filtered = FXCollections.observableArrayList();
        for (Reservation r : tableData) {
            if (r.getGuest().getName().toLowerCase().contains(query) ||
                    String.valueOf(r.getId()).contains(query)) {
                filtered.add(r);
            }
        }
        reservationTable.setItems(filtered);
    }

    @FXML
    private void handleCancelReservation() {
        Reservation selected = reservationTable.getSelectionModel().getSelectedItem();

        // Reset styles
        statusLabel.getStyleClass().removeAll("error", "success");

        if (selected == null) {
            statusLabel.setText("Please select a reservation to cancel.");
            statusLabel.getStyleClass().add("error");
            return;
        }

        if (selected.getStatus() == ReservationStatus.CANCELLED) {
            statusLabel.setText("Reservation is already cancelled.");
            statusLabel.getStyleClass().add("error");
            return;
        }

        try {
            // 1. Call the Service to update the Database
            reservationService.cancelReservation(selected);

            // 2. Reload data from DB to ensure table is accurate
            loadData();

            // 3. Show success message
            statusLabel.setText("Reservation #" + selected.getId() + " cancelled successfully.");
            statusLabel.getStyleClass().add("success");

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.getStyleClass().add("error");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}