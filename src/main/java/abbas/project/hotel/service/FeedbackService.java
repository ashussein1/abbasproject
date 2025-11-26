package abbas.project.hotel.service;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.events.FeedbackSubmittedEvent;
import abbas.project.hotel.model.Feedback;
import abbas.project.hotel.model.Guest;
import abbas.project.hotel.repository.FeedbackRepository;
import com.google.inject.Inject;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Objects;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DomainEventPublisher publisher;

    @Inject
    public FeedbackService(FeedbackRepository feedbackRepository,
                           DomainEventPublisher publisher) {
        this.feedbackRepository = feedbackRepository;
        this.publisher = publisher;
    }

    public ObservableList<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public void addFeedback(Feedback feedback) {
        feedbackRepository.add(feedback);
        publisher.publish(new FeedbackSubmittedEvent(feedback));
    }

    /**
     * Convenience for kiosk / feedback-only view – creates a simple anonymous guest
     * and publishes a FeedbackSubmittedEvent.
     */
    public void submitAnonymousFeedback(int rating, String comment) {
        Guest guest = new Guest(null, "Anonymous Guest", "", "");

        long nextId = feedbackRepository.findAll().stream()
                .map(Feedback::getId)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;

        Feedback feedback = new Feedback(nextId, guest, rating, comment, LocalDate.now());
        feedbackRepository.add(feedback);
        publisher.publish(new FeedbackSubmittedEvent(feedback));
    }
}
