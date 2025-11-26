package abbas.project.hotel.app;

import abbas.project.hotel.events.DomainEventPublisher;
import abbas.project.hotel.repository.*;
import abbas.project.hotel.security.AdminAuthService;
import abbas.project.hotel.service.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AppConfig extends AbstractModule {

    @Override
    protected void configure() {

        // Repositories (in-memory for now, but structured as a persistence layer)
        bind(FeedbackRepository.class).in(Singleton.class);
        bind(GuestRepository.class).in(Singleton.class);
        bind(ReservationRepository.class).in(Singleton.class);
        bind(RoomRepository.class).in(Singleton.class);

        // Core services
        bind(DomainEventPublisher.class).in(Singleton.class);
        bind(ActivityLogService.class).in(Singleton.class);
        bind(FeedbackService.class).in(Singleton.class);
        bind(ReservationService.class).in(Singleton.class);
        bind(BillingService.class).in(Singleton.class);
        bind(LoyaltyService.class).in(Singleton.class);
        bind(ReportingService.class).in(Singleton.class);

        // Security
        bind(AdminAuthService.class).in(Singleton.class);
    }
}
