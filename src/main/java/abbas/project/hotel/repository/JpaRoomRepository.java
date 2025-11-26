package abbas.project.hotel.repository;

import abbas.project.hotel.model.Room;
import abbas.project.hotel.model.RoomType;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaRoomRepository implements RoomRepository {

    private final EntityManagerFactory emf;

    public JpaRoomRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Room save(Room room) {
        return JpaUtil.tx(emf, em -> {
            if (room.getId() == null) {
                em.persist(room);
                return room;
            } else {
                return em.merge(room);
            }
        });
    }

    @Override
    public Optional<Room> findById(Long id) {
        return JpaUtil.tx(emf, em -> Optional.ofNullable(em.find(Room.class, id)));
    }

    @Override
    public Optional<Room> findByRoomNumber(String number) {
        return JpaUtil.tx(emf, em -> {
            TypedQuery<Room> q = em.createQuery(
                    "select r from Room r where r.roomNumber = :n", Room.class);
            q.setParameter("n", number);
            List<Room> list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        });
    }

    @Override
    public List<Room> findByType(RoomType type) {
        return JpaUtil.tx(emf, em -> em.createQuery(
                        "select r from Room r where r.type = :t", Room.class)
                .setParameter("t", type)
                .getResultList());
    }

    @Override
    public List<Room> findAll() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select r from Room r", Room.class).getResultList());
    }

    @Override
    public long count() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select count(r) from Room r", Long.class).getSingleResult());
    }
}
