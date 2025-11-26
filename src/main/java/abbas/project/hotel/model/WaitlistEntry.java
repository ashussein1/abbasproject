package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "waitlist")
public class WaitlistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Guest guest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType desiredType;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    private boolean notified;

    protected WaitlistEntry() {}

    public WaitlistEntry(Guest guest, RoomType desiredType,
                         LocalDate fromDate, LocalDate toDate) {
        this.guest = guest;
        this.desiredType = desiredType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.notified = false;
    }

    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public RoomType getDesiredType() { return desiredType; }
    public LocalDate getFromDate() { return fromDate; }
    public LocalDate getToDate() { return toDate; }
    public boolean isNotified() { return notified; }
    public void setNotified(boolean notified) { this.notified = notified; }
}
