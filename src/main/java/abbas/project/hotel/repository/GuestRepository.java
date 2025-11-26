package abbas.project.hotel.repository;

import abbas.project.hotel.model.Guest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GuestRepository {
    private final ObservableList<Guest> guests = FXCollections.observableArrayList();

    public GuestRepository() {
        guests.add(new Guest(1L, "John Doe", "416-555-1000", "john@example.com"));
        guests.add(new Guest(2L, "Sara Ali", "647-555-2000", "sara@example.com"));
    }

    public ObservableList<Guest> findAll() {
        return guests;
    }
}