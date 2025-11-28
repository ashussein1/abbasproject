package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST) // If we save reservation, save the guest too
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private double totalEstimate;

    // Required by Hibernate
    public Reservation() {}

    public Reservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut, ReservationStatus status, double totalEstimate) {
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.totalEstimate = totalEstimate;
    }

    // Getters
    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public ReservationStatus getStatus() { return status; }
    public double getTotalEstimate() { return totalEstimate; }

    public void setStatus(ReservationStatus status) { this.status = status; }
}