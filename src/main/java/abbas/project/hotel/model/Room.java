package abbas.project.hotel.model;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private int maxOccupancy;

    protected Room() {
    }

    public Room(String roomNumber, RoomType type, double basePrice, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.basePrice = basePrice;
        this.maxOccupancy = maxOccupancy;
    }

    public Long getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public double getBasePrice() { return basePrice; }
    public int getMaxOccupancy() { return maxOccupancy; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setType(RoomType type) { this.type = type; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
    public void setMaxOccupancy(int maxOccupancy) { this.maxOccupancy = maxOccupancy; }
}
