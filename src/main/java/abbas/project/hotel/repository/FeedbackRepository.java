package abbas.project.hotel.repository;

import abbas.project.hotel.model.Feedback;
import abbas.project.hotel.util.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.List;

public class FeedbackRepository {

    public ObservableList<Feedback> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Feedback> list = em.createQuery("SELECT f FROM Feedback f", Feedback.class).getResultList();
            return FXCollections.observableArrayList(list);
        } finally {
            em.close();
        }
    }

    public void save(Feedback feedback) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(feedback);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}