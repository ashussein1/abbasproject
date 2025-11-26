package abbas.project.hotel.events;

import abbas.project.hotel.model.Feedback;

public class FeedbackSubmittedEvent {

    private final Feedback feedback;

    public FeedbackSubmittedEvent(Feedback feedback) {
        this.feedback = feedback;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "FeedbackSubmittedEvent{rating=" + feedback.getRating() + "}";
    }
}
