package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Guest guest;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Reservation reservation;

    @Column(nullable = false)
    private int rating;      // 1–5

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDate date;

    protected Feedback() {}

    public Feedback(Guest guest, Reservation reservation, int rating,
                    String comment, LocalDate date) {
        this.guest = guest;
        this.reservation = reservation;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public Reservation getReservation() { return reservation; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDate getDate() { return date; }
}
