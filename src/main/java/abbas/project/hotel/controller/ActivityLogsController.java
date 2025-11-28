package abbas.project.hotel.controller;

import abbas.project.hotel.model.LogEntry;
import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.model.ReservationStatus;
import abbas.project.hotel.repository.LogRepository;
import abbas.project.hotel.service.ReservationService;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityLogsController {

    private final LogRepository logRepository;
    private final ReservationService reservationService;
    private final ObservableList<LogEntry> logs = FXCollections.observableArrayList();

    // Logs Tab
    @FXML private TableView<LogEntry> logTable;
    @FXML private TableColumn<LogEntry, String> timeColumn;
    @FXML private TableColumn<LogEntry, String> actionColumn;
    @FXML private TableColumn<LogEntry, String> detailsColumn;

    // Revenue Tab
    @FXML private Label dailyRevenueLabel;
    @FXML private Label weeklyRevenueLabel;
    @FXML private Label monthlyRevenueLabel;

    @Inject
    public ActivityLogsController(LogRepository logRepository, ReservationService reservationService) {
        this.logRepository = logRepository;
        this.reservationService = reservationService;
    }

    @FXML
    private void initialize() {
        setupLogsTable();
        calculateRevenue();
    }

    private void setupLogsTable() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        timeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTimestamp().format(formatter)));

        actionColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getAction()));

        detailsColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDetails()));

        logs.setAll(logRepository.findAll());
        logTable.setItems(logs);
    }

    private void calculateRevenue() {
        List<Reservation> allReservations = reservationService.getAllReservations();
        LocalDate today = LocalDate.now();

        double dailyTotal = 0;
        double weeklyTotal = 0;
        double monthlyTotal = 0;

        for (Reservation r : allReservations) {
            // Only count valid bookings (not cancelled ones)
            if (r.getStatus() == ReservationStatus.CANCELLED) continue;

            // Basic logic: calculate based on check-in date
            if (r.getCheckIn() != null) {
                boolean isToday = r.getCheckIn().isEqual(today);
                // "This Week" = Last 7 days window
                boolean isThisWeek = r.getCheckIn().isAfter(today.minusDays(7));
                // "This Month" = Same Month and Year
                boolean isThisMonth = r.getCheckIn().getMonth() == today.getMonth() &&
                        r.getCheckIn().getYear() == today.getYear();

                if (isToday) dailyTotal += r.getTotalEstimate();
                if (isThisWeek) weeklyTotal += r.getTotalEstimate();
                if (isThisMonth) monthlyTotal += r.getTotalEstimate();
            }
        }

        if (dailyRevenueLabel != null) dailyRevenueLabel.setText(String.format("$%.2f", dailyTotal));
        if (weeklyRevenueLabel != null) weeklyRevenueLabel.setText(String.format("$%.2f", weeklyTotal));
        if (monthlyRevenueLabel != null) monthlyRevenueLabel.setText(String.format("$%.2f", monthlyTotal));
    }

    @FXML
    private void handleExport() {
        // Get the current stage window
        Stage stage = (Stage) logTable.getScene().getWindow();

        // Use our new utility to export the log table
        abbas.project.hotel.util.DataExporter.exportTableToCSV(logTable, stage);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}