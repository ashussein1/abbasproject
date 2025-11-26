package abbas.project.hotel.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class DateUtils {

    private DateUtils() { }

    /**
     * Nights between two dates. At least 1 night if dates are equal or reversed.
     */
    public static long nightsBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        long days = ChronoUnit.DAYS.between(start, end);
        return (days <= 0) ? 1 : days;
    }
}
