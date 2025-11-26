package abbas.project.hotel.service;

import abbas.project.hotel.model.Reservation;

public class StandardBillingStrategy implements BillingStrategy {

    private final double taxRate;

    public StandardBillingStrategy(double taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public double calculate(Reservation reservation, double baseRoomTotal) {
        double tax = baseRoomTotal * taxRate;
        return baseRoomTotal + tax;
    }
}
