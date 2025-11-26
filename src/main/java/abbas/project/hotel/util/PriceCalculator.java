package abbas.project.hotel.util;

import abbas.project.hotel.model.Room;

import java.time.LocalDate;

public final class PriceCalculator {

    private PriceCalculator() { }

    public static double estimateTotal(Room room, LocalDate checkIn, LocalDate checkOut) {
        if (room == null) {
            return 0.0;
        }
        long nights = DateUtils.nightsBetween(checkIn, checkOut);
        return room.getBasePrice() * nights;
    }
}
