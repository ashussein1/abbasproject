package abbas.project.hotel.app;

import abbas.project.hotel.repository.*;
import abbas.project.hotel.service.*;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AppConfig extends AbstractModule {

    @Override
    protected void configure() {
        // JPA EntityManagerFactory as singleton
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hotelPU");
        bind(EntityManagerFactory.class).toInstance(emf);

        // Repository bindings
        bind(GuestRepository.class).to(JpaGuestRepository.class).in(Scopes.SINGLETON);
        bind(RoomRepository.class).to(JpaRoomRepository.class).in(Scopes.SINGLETON);
        bind(ReservationRepository.class).to(JpaReservationRepository.class).in(Scopes.SINGLETON);
        bind(FeedbackRepository.class).to(JpaFeedbackRepository.class).in(Scopes.SINGLETON);
        bind(AdminUserRepository.class).to(JpaAdminUserRepository.class).in(Scopes.SINGLETON);
        bind(ActivityLogRepository.class).to(JpaActivityLogRepository.class).in(Scopes.SINGLETON);

        // Services (Guice will inject dependencies automatically)
        bind(ActivityLogService.class).in(Scopes.SINGLETON);
        bind(AuthService.class).in(Scopes.SINGLETON);
        bind(BillingService.class).in(Scopes.SINGLETON);
        bind(LoyaltyService.class).in(Scopes.SINGLETON);
        bind(ReservationService.class).in(Scopes.SINGLETON);
        bind(FeedbackService.class).in(Scopes.SINGLETON);
        bind(RoomFactory.class).in(Scopes.SINGLETON);
    }
}
