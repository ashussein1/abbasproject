package abbas.project.hotel.repository;

import abbas.project.hotel.model.Feedback;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaFeedbackRepository implements FeedbackRepository {

    private final EntityManagerFactory emf;

    public JpaFeedbackRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Feedback save(Feedback feedback) {
        return JpaUtil.tx(emf, em -> {
            if (feedback.getId() == null) {
                em.persist(feedback);
                return feedback;
            } else {
                return em.merge(feedback);
            }
        });
    }

    @Override
    public List<Feedback> findAll() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select f from Feedback f", Feedback.class).getResultList());
    }

    @Override
    public long count() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select count(f) from Feedback f", Long.class).getSingleResult());
    }
}
