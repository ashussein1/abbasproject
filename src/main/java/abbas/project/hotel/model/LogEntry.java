package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String action; // e.g., "Reservation Created"
    private String details; // e.g., "Guest: John Doe"

    public LogEntry() {}

    public LogEntry(String action, String details) {
        this.timestamp = LocalDateTime.now();
        this.action = action;
        this.details = details;
    }

    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getAction() { return action; }
    public String getDetails() { return details; }
}