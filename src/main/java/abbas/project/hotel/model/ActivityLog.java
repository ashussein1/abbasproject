package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String actor;         // username
    private String action;        // LOGIN, CREATE_RESERVATION, etc.
    private String entityType;    // Reservation, Guest, etc.
    private String entityId;      // id as string
    @Column(length = 1000)
    private String message;

    protected ActivityLog() {}

    public ActivityLog(LocalDateTime timestamp, String actor,
                       String action, String entityType,
                       String entityId, String message) {
        this.timestamp = timestamp;
        this.actor = actor;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.message = message;
    }
}
