package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST) // Save guest info if needed
    @JoinColumn(name = "guest_id")
    private Guest guest;

    private int rating; // 1–5
    private String comment;
    private LocalDate date;

    // Required by Hibernate
    public Feedback() {}

    public Feedback(Guest guest, int rating, String comment, LocalDate date) {
        this.guest = guest;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    // Getters
    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDate getDate() { return date; }
}