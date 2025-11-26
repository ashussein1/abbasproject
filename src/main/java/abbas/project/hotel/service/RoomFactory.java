package abbas.project.hotel.service;

import abbas.project.hotel.model.Room;
import abbas.project.hotel.model.RoomType;

public class RoomFactory {

    public Room createRoom(String number, RoomType type) {
        switch (type) {
            case SINGLE:
                return new Room(number, type, 120.0, 2);
            case DOUBLE:
                return new Room(number, type, 180.0, 4);
            case DELUXE:
                return new Room(number, type, 260.0, 2);
            case PENTHOUSE:
                return new Room(number, type, 400.0, 2);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
