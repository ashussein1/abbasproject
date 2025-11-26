package abbas.project.hotel.service;

import abbas.project.hotel.model.ActivityLog;
import abbas.project.hotel.repository.ActivityLogRepository;
import abbas.project.hotel.util.Logging;

import com.google.inject.Inject;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class ActivityLogService {

    private final ActivityLogRepository repository;
    private final Logger logger = Logging.getLogger(ActivityLogService.class);

    @Inject
    public ActivityLogService(ActivityLogRepository repository) {
        this.repository = repository;
    }

    public void log(String actor, String action,
                    String entityType, String entityId, String message) {
        ActivityLog log = new ActivityLog(
                LocalDateTime.now(), actor, action, entityType, entityId, message);
        repository.save(log);
        logger.info(actor + " " + action + " [" + entityType + "#" + entityId + "]: " + message);
    }
}
