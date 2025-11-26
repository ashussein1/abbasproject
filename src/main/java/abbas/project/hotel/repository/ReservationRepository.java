package abbas.project.hotel.repository;

import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.model.ReservationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findBetween(LocalDate from, LocalDate to);
    List<Reservation> findByStatus(ReservationStatus status);
    long countByStatus(ReservationStatus status);
}
