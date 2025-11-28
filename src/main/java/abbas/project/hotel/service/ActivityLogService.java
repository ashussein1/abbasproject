package abbas.project.hotel.service;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.events.FeedbackSubmittedEvent;
import abbas.project.hotel.events.ReservationCreatedEvent;
import abbas.project.hotel.model.LogEntry;
import abbas.project.hotel.repository.LogRepository;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ActivityLogService {

    private static final Logger logger = Logger.getLogger("HotelSystemLog");
    private final LogRepository logRepository;

    @Inject
    public ActivityLogService(DomainEventPublisher publisher, LogRepository logRepository) {
        this.logRepository = logRepository;
        setupLogger();

        // Listen for events and log them automatically
        publisher.register(event -> {
            if (event instanceof ReservationCreatedEvent) {
                ReservationCreatedEvent e = (ReservationCreatedEvent) event;
                String msg = "Reservation created: ID #" + e.getReservation().getId()
                        + " for " + e.getReservation().getGuest().getName();
                logInfo(msg, "Reservation");
            } else if (event instanceof FeedbackSubmittedEvent) {
                FeedbackSubmittedEvent e = (FeedbackSubmittedEvent) event;
                String msg = "Feedback submitted: Rating " + e.getFeedback().getRating();
                logInfo(msg, "Feedback");
            }
        });
    }

    private void setupLogger() {
        try {
            // File logging (Requirement: 1MB limit, 10 files)
            FileHandler fileHandler = new FileHandler("system_logs.%g.log", 1024 * 1024, 10, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    // Updated logInfo to save to DB as well
    public void logInfo(String message, String category) {
        // 1. Write to File
        logger.info("[" + category + "] " + message);

        // 2. Write to Database
        try {
            LogEntry entry = new LogEntry(category, message);
            logRepository.save(entry);
        } catch (Exception e) {
            System.err.println("Failed to save log to DB: " + e.getMessage());
        }
    }

    // Helper for general info
    public void logInfo(String message) {
        logInfo(message, "General");
    }

    public void logError(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }
}