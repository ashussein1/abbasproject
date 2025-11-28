package abbas.project.hotel.model;

public enum RoomType {
    SINGLE(2),
    DOUBLE(5),
    DELUXE(3),
    PENTHOUSE(8);

    private final int capacity;

    RoomType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}