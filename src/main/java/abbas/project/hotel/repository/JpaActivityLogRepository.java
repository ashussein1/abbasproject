package abbas.project.hotel.repository;

import abbas.project.hotel.model.ActivityLog;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaActivityLogRepository implements ActivityLogRepository {

    private final EntityManagerFactory emf;

    public JpaActivityLogRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public ActivityLog save(ActivityLog log) {
        return JpaUtil.tx(emf, em -> {
            if (log == null) return null;
            em.persist(log);
            return log;
        });
    }

    @Override
    public List<ActivityLog> findAll() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select l from ActivityLog l order by l.timestamp desc",
                        ActivityLog.class).getResultList());
    }
}
