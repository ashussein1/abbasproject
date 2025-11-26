package abbas.project.hotel.repository;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.model.ReservationStatus;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JpaReservationRepository implements ReservationRepository {

    private final EntityManagerFactory emf;

    public JpaReservationRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return JpaUtil.tx(emf, em -> {
            if (reservation.getId() == null) {
                em.persist(reservation);
                return reservation;
            } else {
                return em.merge(reservation);
            }
        });
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return JpaUtil.tx(emf, em ->
                Optional.ofNullable(em.find(Reservation.class, id)));
    }

    @Override
    public List<Reservation> findAll() {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select r from Reservation r", Reservation.class).getResultList());
    }

    @Override
    public List<Reservation> findBetween(LocalDate from, LocalDate to) {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select r from Reservation r " +
                                        "where r.checkIn >= :from and r.checkOut <= :to",
                                Reservation.class)
                        .setParameter("from", from)
                        .setParameter("to", to)
                        .getResultList());
    }

    @Override
    public List<Reservation> findByStatus(ReservationStatus status) {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select r from Reservation r where r.status = :s",
                                Reservation.class)
                        .setParameter("s", status)
                        .getResultList());
    }

    @Override
    public long countByStatus(ReservationStatus status) {
        return JpaUtil.tx(emf, em ->
                em.createQuery("select count(r) from Reservation r where r.status = :s",
                                Long.class)
                        .setParameter("s", status)
                        .getSingleResult());
    }
}
