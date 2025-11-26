package abbas.project.hotel.service;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.events.FeedbackSubmittedEvent;
import abbas.project.hotel.events.ReservationCreatedEvent;
import com.google.inject.Inject;

/**
 * Simple logger that listens to domain events and writes to console.
 * Later this could append to log files, DB, etc.
 */
public class ActivityLogService {

    @Inject
    public ActivityLogService(DomainEventPublisher publisher) {
        publisher.register(event -> {
            if (event instanceof ReservationCreatedEvent) {
                ReservationCreatedEvent e = (ReservationCreatedEvent) event;
                System.out.println("[LOG] Reservation created: #" + e.getReservation().getId());
            } else if (event instanceof FeedbackSubmittedEvent) {
                FeedbackSubmittedEvent e = (FeedbackSubmittedEvent) event;
                System.out.println("[LOG] Feedback submitted: rating=" + e.getFeedback().getRating());
            } else {
                System.out.println("[LOG] Event: " + event);
            }
        });
    }

    public void logMessage(String message) {
        System.out.println("[LOG] " + message);
    }
}
