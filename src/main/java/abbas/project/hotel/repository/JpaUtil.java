package abbas.project.hotel.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;
import java.util.function.Function;

public class JpaUtil {

    public static <T> T tx(EntityManagerFactory emf, Function<EntityManager, T> work) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T result = work.apply(em);
            tx.commit();
            return result;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public static void txVoid(EntityManagerFactory emf, Consumer<EntityManager> work) {
        tx(emf, em -> { work.accept(em); return null; });
    }
}
