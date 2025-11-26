package abbas.project.hotel.service;

import abbas.project.hotel.model.Feedback;
import abbas.project.hotel.model.Guest;
import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.repository.FeedbackRepository;
import abbas.project.hotel.repository.ReservationRepository;

import com.google.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ReservationRepository reservationRepository;
    private final ActivityLogService activityLogService;

    @Inject
    public FeedbackService(FeedbackRepository feedbackRepository,
                           ReservationRepository reservationRepository,
                           ActivityLogService activityLogService) {
        this.feedbackRepository = feedbackRepository;
        this.reservationRepository = reservationRepository;
        this.activityLogService = activityLogService;
    }

    public Feedback submitFeedback(long reservationId, int rating, String comment) {
        Optional<Reservation> opt = reservationRepository.findById(reservationId);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found: " + reservationId);
        }
        Reservation reservation = opt.get();
        Guest guest = reservation.getGuest();

        Feedback feedback = new Feedback(guest, reservation, rating,
                comment, LocalDate.now());
        Feedback saved = feedbackRepository.save(feedback);

        activityLogService.log(guest.getName(), "FEEDBACK_SUBMIT",
                "Feedback", String.valueOf(saved.getId()),
                "Rating " + rating);

        return saved;
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public long count() {
        return feedbackRepository.count();
    }
}
