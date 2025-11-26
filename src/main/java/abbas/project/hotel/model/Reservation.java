package abbas.project.hotel.model;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Guest guest;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private ReservationStatus status;
    private double totalEstimate;

    public Reservation(Long id, Guest guest, Room room, LocalDate checkIn, LocalDate checkOut, ReservationStatus status, double totalEstimate) {
        this.id = id;
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
}