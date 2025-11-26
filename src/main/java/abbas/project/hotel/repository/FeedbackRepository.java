package abbas.project.hotel.repository;

import abbas.project.hotel.model.Feedback;
import abbas.project.hotel.model.Guest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class FeedbackRepository {
    private final ObservableList<Feedback> feedbackList = FXCollections.observableArrayList();

    public FeedbackRepository() {
        Guest dummy = new Guest(1L, "John Doe", "416-555-1000", "john@example.com");
        feedbackList.add(new Feedback(1L, dummy, 5, "Great stay!", LocalDate.now().minusDays(2)));
    }

    public ObservableList<Feedback> findAll() {
        return feedbackList;
    }

    public void add(Feedback feedback) {
        feedbackList.add(feedback);
    }
}