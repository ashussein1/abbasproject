package abbas.project.hotel.service;

import abbas.project.hotel.model.Reservation;

public interface BillingStrategy {
    double calculate(Reservation reservation, double baseRoomTotal);
}
