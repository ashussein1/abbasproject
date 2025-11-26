package abbas.project.hotel.events;

import abbas.project.hotel.model.Reservation;

public class ReservationCreatedEvent {

    private final Reservation reservation;

    public ReservationCreatedEvent(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    @Override
    public String toString() {
        return "ReservationCreatedEvent{id=" + reservation.getId() + "}";
    }
}
