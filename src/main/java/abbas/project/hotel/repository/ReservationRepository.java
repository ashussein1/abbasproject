package abbas.project.hotel.repository;

import abbas.project.hotel.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReservationRepository {

    private final ObservableList<Reservation> reservations =
            FXCollections.observableArrayList();

    public ReservationRepository() {
        // sample demo data
        Guest g1 = new Guest(1L, "John Doe", "416-555-1000", "john@example.com");
        Guest g2 = new Guest(2L, "Sara Ali", "647-555-2000", "sara@example.com");

        Room r1 = new Room(1L, "101", RoomType.SINGLE, 120.0);
        Room r2 = new Room(2L, "205", RoomType.DOUBLE, 180.0);

        reservations.add(new Reservation(
                1L, g1, r1, LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3), ReservationStatus.CONFIRMED, 260.0));

        reservations.add(new Reservation(
                2L, g2, r2, LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(5), ReservationStatus.PENDING, 540.0));
    }

    public ObservableList<Reservation> findAll() {
        return reservations;
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }
}
