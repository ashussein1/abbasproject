package abbas.project.hotel.service;

import abbas.project.hotel.model.AdminUser;
import abbas.project.hotel.model.Role;
import abbas.project.hotel.repository.AdminUserRepository;
import abbas.project.hotel.util.Logging;
import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

public class AuthService {

    private final AdminUserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final Logger logger = Logging.getLogger(AuthService.class);

    @Inject
    public AuthService(AdminUserRepository userRepository,
                       ActivityLogService activityLogService) {
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        ensureDefaultUsers();
    }

    private void ensureDefaultUsers() {
        if (userRepository.count() == 0) {
            AdminUser admin = new AdminUser(
                    "admin",
                    BCrypt.hashpw("admin123", BCrypt.gensalt()),
                    Role.ADMIN);
            AdminUser manager = new AdminUser(
                    "manager",
                    BCrypt.hashpw("manager123", BCrypt.gensalt()),
                    Role.MANAGER);
            userRepository.save(admin);
            userRepository.save(manager);
            logger.info("Seeded default admin users.");
        }
    }

    public Optional<AdminUser> login(String username, String password) {
        Optional<AdminUser> opt = userRepository.findByUsername(username);
        if (opt.isEmpty()) {
            activityLogService.log("SYSTEM", "LOGIN_FAIL", "AdminUser", "-", "Unknown user: " + username);
            return Optional.empty();
        }
        AdminUser user = opt.get();
        boolean matches = BCrypt.checkpw(password, user.getPasswordHash());
        if (!matches) {
            activityLogService.log(username, "LOGIN_FAIL", "AdminUser", String.valueOf(user.getId()),
                    "Incorrect password");
            return Optional.empty();
        }
        activityLogService.log(username, "LOGIN_SUCCESS", "AdminUser", String.valueOf(user.getId()), "Logged in");
        return Optional.of(user);
    }
}
