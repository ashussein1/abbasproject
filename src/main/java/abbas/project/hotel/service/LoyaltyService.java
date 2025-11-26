package abbas.project.hotel.service;

import abbas.project.hotel.model.Guest;
import abbas.project.hotel.model.LoyaltyAccount;
import abbas.project.hotel.repository.GuestRepository;
import abbas.project.hotel.repository.ReservationRepository;

import com.google.inject.Inject;

public class LoyaltyService {

    // For brevity we won’t implement full repository – this is a simple stub
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Inject
    public LoyaltyService(GuestRepository guestRepository,
                          ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public int calculatePoints(double paidAmount) {
        // 1 point per $10
        return (int) (paidAmount / 10.0);
    }

    public LoyaltyAccount createAccountForGuest(Guest guest) {
        return new LoyaltyAccount(guest);
    }
}
