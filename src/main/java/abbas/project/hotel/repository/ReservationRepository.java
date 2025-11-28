package abbas.project.hotel.repository;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.util.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.List;

public class ReservationRepository {

    public ObservableList<Reservation> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Reservation> list = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
            return FXCollections.observableArrayList(list);
        } finally {
            em.close();
        }
    }

    // For NEW reservations
    public void save(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void update(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reservation); // 'merge' updates an existing record
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}