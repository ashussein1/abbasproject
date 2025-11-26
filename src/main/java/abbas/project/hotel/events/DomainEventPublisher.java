package abbas.project.hotel.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Very simple domain event publisher.
 * Services publish events; ActivityLogService (and others) listen.
 */
public class DomainEventPublisher {

    private final List<Consumer<Object>> listeners = new CopyOnWriteArrayList<>();

    public void register(Consumer<Object> listener) {
        listeners.add(listener);
    }

    public void publish(Object event) {
        for (Consumer<Object> listener : listeners) {
            listener.accept(event);
        }
    }
}
