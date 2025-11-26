package abbas.project.hotel.model;

public class Room {
    private Long id;
    private String roomNumber;
    private RoomType type;
    private double basePrice;

    public Room(Long id, String roomNumber, RoomType type, double basePrice) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.basePrice = basePrice;
    }

    public Long getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public double getBasePrice() { return basePrice; }
}