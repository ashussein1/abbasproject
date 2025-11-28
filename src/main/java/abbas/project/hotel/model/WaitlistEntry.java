package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "waitlist")
public class WaitlistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;
    private String guestPhone;
    private String guestEmail;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private int guestCount;

    private LocalDateTime requestDate;

    public WaitlistEntry() {}

    public WaitlistEntry(String name, String phone, String email, LocalDate checkIn, LocalDate checkOut, int guestCount) {
        this.guestName = name;
        this.guestPhone = phone;
        this.guestEmail = email;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestCount = guestCount;
        this.requestDate = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getGuestName() { return guestName; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
}