package abbas.project.hotel.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    // This is the Singleton instance
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hotel-pu");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void shutdown() {
        if (emf != null && !emf.isOpen()) {
            emf.close();
        }
    }
}