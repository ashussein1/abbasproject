package abbas.project.hotel.security;

import abbas.project.hotel.model.User;
import abbas.project.hotel.repository.UserRepository;
import abbas.project.hotel.service.ActivityLogService;
import com.google.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

public class AdminAuthService {

    private final UserRepository userRepository;
    private final ActivityLogService logger;
    private User currentUser;

    @Inject
    public AdminAuthService(UserRepository userRepository, ActivityLogService logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            this.currentUser = user;
            logger.logInfo("Login successful for user: " + username);
            return true;
        }

        logger.logInfo("Login failed for user: " + username);
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        if (currentUser != null) {
            logger.logInfo("User logged out: " + currentUser.getUsername());
        }
        currentUser = null;
    }
}