package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Guest guest;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Room room;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private double totalEstimate;

    protected Reservation() {}

    public Reservation(Guest guest, Room room,
                       LocalDate checkIn, LocalDate checkOut,
                       ReservationStatus status, double totalEstimate) {
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.totalEstimate = totalEstimate;
    }

    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public ReservationStatus getStatus() { return status; }
    public double getTotalEstimate() { return totalEstimate; }

    public void setStatus(ReservationStatus status) { this.status = status; }
    public void setTotalEstimate(double totalEstimate) { this.totalEstimate = totalEstimate; }
}
