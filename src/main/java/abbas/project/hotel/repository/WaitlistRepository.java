package abbas.project.hotel.repository;

import abbas.project.hotel.model.WaitlistEntry;
import abbas.project.hotel.util.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.List;

public class WaitlistRepository {

    public void save(WaitlistEntry entry) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entry);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public ObservableList<WaitlistEntry> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<WaitlistEntry> list = em.createQuery("SELECT w FROM WaitlistEntry w", WaitlistEntry.class).getResultList();
            return FXCollections.observableArrayList(list);
        } finally {
            em.close();
        }
    }
}