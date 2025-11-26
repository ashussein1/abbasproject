package abbas.project.hotel.repository;

import abbas.project.hotel.model.AdminUser;

import java.util.Optional;

public interface AdminUserRepository {
    AdminUser save(AdminUser user);
    Optional<AdminUser> findByUsername(String username);
    long count();
}
