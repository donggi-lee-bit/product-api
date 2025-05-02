package donggi.lee.catalog.user.domain.repository;

import donggi.lee.catalog.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);
}
