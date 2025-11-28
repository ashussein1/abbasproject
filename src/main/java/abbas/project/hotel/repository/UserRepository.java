package abbas.project.hotel.repository;

import abbas.project.hotel.model.User;
import abbas.project.hotel.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserRepository {

    public void save(User user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public User findByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // User not found
        } finally {
            em.close();
        }
    }

    // Check if table is empty (to seed default admin)
    public boolean isEmpty() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
            return count == 0;
        } finally {
            em.close();
        }
    }
}