package abbas.project.hotel.repository;

import abbas.project.hotel.model.Room;
import abbas.project.hotel.model.RoomType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomRepository {
    private final ObservableList<Room> rooms = FXCollections.observableArrayList();

    public RoomRepository() {
        rooms.add(new Room(1L, "101", RoomType.SINGLE, 120));
        rooms.add(new Room(2L, "102", RoomType.SINGLE, 120));
        rooms.add(new Room(3L, "201", RoomType.DOUBLE, 180));
        rooms.add(new Room(4L, "301", RoomType.DELUXE, 260));
    }

    public ObservableList<Room> findAll() {
        return rooms;
    }
}
