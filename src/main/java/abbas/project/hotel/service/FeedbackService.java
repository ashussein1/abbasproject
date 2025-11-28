package abbas.project.hotel.service;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.events.FeedbackSubmittedEvent;
import abbas.project.hotel.model.Feedback;
import abbas.project.hotel.model.Guest;
import abbas.project.hotel.repository.FeedbackRepository;
import com.google.inject.Inject;
import javafx.collections.ObservableList;

import java.time.LocalDate;

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
        feedbackRepository.save(feedback);
        publisher.publish(new FeedbackSubmittedEvent(feedback));
    }

    public void submitAnonymousFeedback(int rating, String comment) {
        // FIX: Removed 'null' from arguments. Guest only needs Name, Phone, Email now.
        Guest guest = new Guest("Anonymous Guest", "", "");

        // Database generates ID automatically, so we don't pass one.
        Feedback feedback = new Feedback(guest, rating, comment, LocalDate.now());

        feedbackRepository.save(feedback);
        publisher.publish(new FeedbackSubmittedEvent(feedback));
    }
}