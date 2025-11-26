package abbas.project.hotel.service;

import abbas.project.hotel.model.Reservation;

import com.google.inject.Inject;
import java.time.temporal.ChronoUnit;

public class BillingService {

    private final BillingStrategy strategy;

    @Inject
    public BillingService() {
        // 13% tax
        this.strategy = new StandardBillingStrategy(0.13);
    }

    public double calculateRoomBase(Reservation reservation) {
        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        if (nights <= 0) nights = 1;
        return reservation.getRoom().getBasePrice() * nights;
    }

    public double calculateWithAddons(Reservation reservation,
                                      boolean breakfast,
                                      boolean wifi) {
        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        if (nights <= 0) nights = 1;

        PriceComponent price = new BaseRoomPriceComponent(
                reservation.getRoom().getBasePrice() * nights);

        if (breakfast) {
            price = new BreakfastAddon(price, 15.0, nights);
        }
        if (wifi) {
            price = new WifiAddon(price, 10.0);
        }

        double baseTotal = price.total();
        return strategy.calculate(reservation, baseTotal);
    }
}
