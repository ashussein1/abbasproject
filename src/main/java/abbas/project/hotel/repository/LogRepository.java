package abbas.project.hotel.repository;

import abbas.project.hotel.model.LogEntry;
import abbas.project.hotel.util.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.List;

public class LogRepository {

    public void save(LogEntry log) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public ObservableList<LogEntry> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Order by newest first
            List<LogEntry> list = em.createQuery("SELECT l FROM LogEntry l ORDER BY l.timestamp DESC", LogEntry.class)
                    .getResultList();
            return FXCollections.observableArrayList(list);
        } finally {
            em.close();
        }
    }
}