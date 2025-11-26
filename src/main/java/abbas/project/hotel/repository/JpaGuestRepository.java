package abbas.project.hotel.repository;

import abbas.project.hotel.model.Guest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaGuestRepository implements GuestRepository {

    private final EntityManagerFactory emf;

    public JpaGuestRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Guest save(Guest guest) {
        return JpaUtil.tx(emf, em -> {
            if (guest.getId() == null) {
                em.persist(guest);
                return guest;
            } else {
                return em.merge(guest);
            }
        });
    }

    @Override
    public Optional<Guest> findById(Long id) {
        return JpaUtil.tx(emf, em -> Optional.ofNullable(em.find(Guest.class, id)));
    }

    @Override
    public Optional<Guest> findByName(String name) {
        return JpaUtil.tx(emf, em -> {
            TypedQuery<Guest> q = em.createQuery(
                    "select g from Guest g where lower(g.name) = :n", Guest.class);
            q.setParameter("n", name.toLowerCase());
            List<Guest> list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        });
    }

    @Override
    public List<Guest> findAll() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select g from Guest g", Guest.class).getResultList());
    }
}
