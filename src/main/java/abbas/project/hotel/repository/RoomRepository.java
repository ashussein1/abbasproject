package abbas.project.hotel.repository;

import abbas.project.hotel.model.Room;
import abbas.project.hotel.util.JPAUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.List;

public class RoomRepository {

    // Retrieve all rooms from Database
    public ObservableList<Room> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Room> dbRooms = em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
            return FXCollections.observableArrayList(dbRooms);
        } finally {
            em.close();
        }
    }

    // Save a new room to Database
    public void save(Room room) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}