package abbas.project.hotel.model;

import javax.persistence.*;

@Entity // <--- Tells Java this is a database table
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <--- Auto-increment ID (1, 2, 3...)
    private Long id;

    @Column(name = "room_number", unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING) // <--- Saves "SINGLE" as text, not a number
    private RoomType type;

    private double basePrice;

    // Empty constructor is REQUIRED by Hibernate
    public Room() {}

    public Room(String roomNumber, RoomType type, double basePrice) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.basePrice = basePrice;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public double getBasePrice() { return basePrice; }
}