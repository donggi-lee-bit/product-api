package donggi.lee.catalog.user.infrastructure;

import donggi.lee.catalog.user.domain.User;
import donggi.lee.catalog.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    Optional<User> findByEmail(String email);
}
