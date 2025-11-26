package abbas.project.hotel.repository;

import abbas.project.hotel.model.Room;
import abbas.project.hotel.model.RoomType;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findById(Long id);
    Optional<Room> findByRoomNumber(String number);
    List<Room> findByType(RoomType type);
    List<Room> findAll();
    long count();
}
