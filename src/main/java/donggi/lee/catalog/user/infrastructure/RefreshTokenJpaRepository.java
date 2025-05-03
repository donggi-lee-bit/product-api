package donggi.lee.catalog.user.infrastructure;

import donggi.lee.catalog.user.domain.RefreshToken;
import donggi.lee.catalog.user.domain.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenRepository {
}
