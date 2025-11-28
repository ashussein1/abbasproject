package abbas.project.hotel.repository;

import abbas.project.hotel.model.Guest;
import abbas.project.hotel.util.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.List;

public class GuestRepository {

    public ObservableList<Guest> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Guest> list = em.createQuery("SELECT g FROM Guest g", Guest.class).getResultList();
            return FXCollections.observableArrayList(list);
        } finally {
            em.close();
        }
    }

    public void save(Guest guest) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(guest);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}