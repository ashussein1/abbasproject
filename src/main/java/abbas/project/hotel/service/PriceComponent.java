package abbas.project.hotel.service;

// Decorator for add-ons
public interface PriceComponent {
    double total();
}

class BaseRoomPriceComponent implements PriceComponent {
    private final double roomTotal;

    BaseRoomPriceComponent(double roomTotal) {
        this.roomTotal = roomTotal;
    }

    @Override
    public double total() {
        return roomTotal;
    }
}

abstract class AddonDecorator implements PriceComponent {
    protected final PriceComponent inner;

    protected AddonDecorator(PriceComponent inner) {
        this.inner = inner;
    }
}

class BreakfastAddon extends AddonDecorator {
    private final double perNight;
    private final long nights;

    BreakfastAddon(PriceComponent inner, double perNight, long nights) {
        super(inner);
        this.perNight = perNight;
        this.nights = nights;
    }

    @Override
    public double total() {
        return inner.total() + perNight * nights;
    }
}

class WifiAddon extends AddonDecorator {
    private final double perStay;

    WifiAddon(PriceComponent inner, double perStay) {
        super(inner);
        this.perStay = perStay;
    }

    @Override
    public double total() {
        return inner.total() + perStay;
    }
}
