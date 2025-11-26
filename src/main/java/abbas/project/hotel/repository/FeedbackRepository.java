package abbas.project.hotel.repository;

import abbas.project.hotel.model.Feedback;

import java.util.List;

public interface FeedbackRepository {
    Feedback save(Feedback feedback);
    List<Feedback> findAll();
    long count();
}
