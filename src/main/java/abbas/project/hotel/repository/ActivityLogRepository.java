package abbas.project.hotel.repository;

import abbas.project.hotel.model.ActivityLog;

import java.util.List;

public interface ActivityLogRepository {
    ActivityLog save(ActivityLog log);
    List<ActivityLog> findAll();
}
