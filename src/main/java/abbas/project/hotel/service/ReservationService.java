package abbas.project.hotel.service;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.events.ReservationCreatedEvent;
import abbas.project.hotel.model.Guest;
import abbas.project.hotel.model.Reservation;
import abbas.project.hotel.model.ReservationStatus;
import abbas.project.hotel.model.Room;
import abbas.project.hotel.repository.ReservationRepository;
import abbas.project.hotel.repository.RoomRepository;
import abbas.project.hotel.util.PriceCalculator;
import com.google.inject.Inject;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final DomainEventPublisher publisher;

    @Inject
    public ReservationService(ReservationRepository reservationRepository,
                              RoomRepository roomRepository,
                              DomainEventPublisher publisher) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.publisher = publisher;
    }

    public ObservableList<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Used from kiosk: creates a simple pending reservation and returns it.
     * (For Milestone 3, this is where we’d involve real guest details, room selection, etc.)
     */
    public Reservation createKioskReservation(int adults,
                                              int children,
                                              LocalDate checkIn,
                                              LocalDate checkOut) {

        Room room = roomRepository.findAll().isEmpty()
                ? null
                : roomRepository.findAll().get(0); // pick first room for demo

        Guest guest = new Guest(null, "Kiosk Guest", "", "");

        long nextId = reservationRepository.findAll().stream()
                .map(Reservation::getId)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;

        double estimate = PriceCalculator.estimateTotal(room, checkIn, checkOut);

        Reservation reservation = new Reservation(
                nextId,
                guest,
                room,
                checkIn,
                checkOut,
                ReservationStatus.PENDING,
                estimate
        );

        reservationRepository.add(reservation);
        publisher.publish(new ReservationCreatedEvent(reservation));

        return reservation;
    }

    /**
     * Simple occupancy metric: how many reservations cover the given date.
     */
    public int countReservationsOn(LocalDate date) {
        return (int) reservationRepository.findAll().stream()
                .filter(res -> {
                    LocalDate in = res.getCheckIn();
                    LocalDate out = res.getCheckOut();
                    return in != null && out != null
                            && !in.isAfter(date)
                            && out.isAfter(date);
                })
                .count();
    }
}
