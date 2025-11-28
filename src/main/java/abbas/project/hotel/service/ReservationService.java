package abbas.project.hotel.service;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.events.ReservationCreatedEvent;
import abbas.project.hotel.model.*;
import abbas.project.hotel.repository.ReservationRepository;
import abbas.project.hotel.repository.RoomRepository;
import abbas.project.hotel.repository.WaitlistRepository; // New Import
import abbas.project.hotel.util.PriceCalculator;
import com.google.inject.Inject;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final WaitlistRepository waitlistRepository; // New Repository
    private final DomainEventPublisher publisher;

    @Inject
    public ReservationService(ReservationRepository reservationRepository,
                              RoomRepository roomRepository,
                              WaitlistRepository waitlistRepository, // Inject here
                              DomainEventPublisher publisher) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.waitlistRepository = waitlistRepository;
        this.publisher = publisher;
    }

    public ObservableList<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createKioskReservation(int adults,
                                              int children,
                                              LocalDate checkIn,
                                              LocalDate checkOut) {
        int totalGuests = adults + children;
        List<Room> allRooms = roomRepository.findAll();

        Room suitableRoom = null;
        for (Room room : allRooms) {
            if (room.getType().getCapacity() >= totalGuests) {
                if (isRoomAvailable(room, checkIn, checkOut)) {
                    suitableRoom = room;
                    break;
                }
            }
        }

        if (suitableRoom == null) {
            throw new IllegalStateException("No available rooms found for " + totalGuests + " guests.");
        }

        Guest guest = new Guest("Kiosk Guest", "555-0000", "kiosk@hotel.com");
        double estimate = PriceCalculator.estimateTotal(suitableRoom, checkIn, checkOut);

        Reservation reservation = new Reservation(
                guest,
                suitableRoom,
                checkIn,
                checkOut,
                ReservationStatus.PENDING,
                estimate
        );

        reservationRepository.save(reservation);
        publisher.publish(new ReservationCreatedEvent(reservation));

        return reservation;
    }

    // --- NEW: Waitlist Logic ---
    public void joinWaitlist(int adults, int children, LocalDate checkIn, LocalDate checkOut) {
        // In a real app, we'd ask for contact info. For Kiosk demo, we use placeholder.
        WaitlistEntry entry = new WaitlistEntry(
                "Waitlist Guest",
                "555-WAIT",
                "wait@list.com",
                checkIn,
                checkOut,
                adults + children
        );
        waitlistRepository.save(entry);
    }

    public void cancelReservation(Reservation reservation) {
        if (reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
            throw new IllegalStateException("Cannot cancel a reservation that is already checked out.");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.update(reservation);
    }

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> existing = reservationRepository.findAll();
        for (Reservation r : existing) {
            if (r.getRoom().getId().equals(room.getId()) &&
                    r.getStatus() != ReservationStatus.CANCELLED &&
                    r.getStatus() != ReservationStatus.CHECKED_OUT) {

                if (checkIn.isBefore(r.getCheckOut()) && checkOut.isAfter(r.getCheckIn())) {
                    return false;
                }
            }
        }
        return true;
    }

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