package abbas.project.hotel.model;

import java.time.LocalDate;

public class Feedback {
    private Long id;
    private Guest guest;
    private int rating; // 1–5
    private String comment;
    private LocalDate date;

    public Feedback(Long id, Guest guest, int rating, String comment, LocalDate date) {
        this.id = id;
        this.guest = guest;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDate getDate() { return date; }
}