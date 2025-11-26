package abbas.project.hotel.repository;

import abbas.project.hotel.model.AdminUser;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class JpaAdminUserRepository implements AdminUserRepository {

    private final EntityManagerFactory emf;

    public JpaAdminUserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public AdminUser save(AdminUser user) {
        return JpaUtil.tx(emf, em -> {
            if (user.getId() == null) {
                em.persist(user);
                return user;
            } else {
                return em.merge(user);
            }
        });
    }

    @Override
    public Optional<AdminUser> findByUsername(String username) {
        return JpaUtil.tx(emf, em -> {
            List<AdminUser> list = em.createQuery(
                            "select a from AdminUser a where a.username = :u", AdminUser.class)
                    .setParameter("u", username)
                    .getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        });
    }

    @Override
    public long count() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select count(a) from AdminUser a", Long.class).getSingleResult());
    }
}
