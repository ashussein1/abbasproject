package abbas.project.hotel.service;

import abbas.project.hotel.model.*;
import abbas.project.hotel.repository.ReservationRepository;
import abbas.project.hotel.repository.RoomRepository;
import abbas.project.hotel.util.Logging;

import com.google.inject.Inject;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final BillingService billingService;
    private final ActivityLogService activityLogService;
    private final Logger logger = Logging.getLogger(ReservationService.class);

    @Inject
    public ReservationService(ReservationRepository reservationRepository,
                              RoomRepository roomRepository,
                              BillingService billingService,
                              ActivityLogService activityLogService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.billingService = billingService;
        this.activityLogService = activityLogService;
    }

    public Optional<Reservation> kioskQuickBook(String guestName,
                                                int adults,
                                                int children,
                                                LocalDate checkIn,
                                                LocalDate checkOut) {
        int totalPeople = adults + children;
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            return Optional.empty();
        }
        if (totalPeople <= 0) {
            return Optional.empty();
        }

        RoomType suggestedType = suggestRoomType(totalPeople);
        List<Room> candidates = roomRepository.findByType(suggestedType);
        if (candidates.isEmpty()) return Optional.empty();

        // naive: pick the first room (no complex conflict check for now)
        Room room = candidates.stream()
                .min(Comparator.comparing(Room::getRoomNumber))
                .orElse(candidates.get(0));

        Guest guest = new Guest(guestName, null, null);

        Reservation reservation = new Reservation(
                guest, room, checkIn, checkOut,
                ReservationStatus.CONFIRMED, 0.0);

        double total = billingService.calculateWithAddons(reservation, false, false);
        reservation.setTotalEstimate(total);

        Reservation saved = reservationRepository.save(reservation);

        activityLogService.log("KIOSK", "CREATE_RESERVATION", "Reservation",
                String.valueOf(saved.getId()),
                "Kiosk booking for " + totalPeople + " people in room "
                        + room.getRoomNumber() + " from " + checkIn + " to " + checkOut);

        logger.info("Created kiosk reservation id=" + saved.getId());
        return Optional.of(saved);
    }

    private RoomType suggestRoomType(int people) {
        if (people == 1) return RoomType.SINGLE;
        if (people == 2) return RoomType.SINGLE; // single room but 2 max
        if (people <= 4) return RoomType.DOUBLE;
        return RoomType.DOUBLE; // For bigger we’d do multiple rooms; simplified
    }

    public long countActiveReservations() {
        return reservationRepository.countByStatus(ReservationStatus.CONFIRMED)
                + reservationRepository.countByStatus(ReservationStatus.CHECKED_IN);
    }
}
