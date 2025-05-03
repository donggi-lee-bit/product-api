package donggi.lee.catalog.user.domain.repository;

import donggi.lee.catalog.user.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    /**
     * 주어진 사용자 ID로 리프레시 토큰을 조회합니다.
     *
     * @param userId 조회할 사용자의 고유 ID
     * @return 해당 사용자의 리프레시 토큰이 있으면 Optional에 담아 반환, 없으면 빈 Optional 반환
     */
    Optional<RefreshToken> findByUserId(final Long userId);

    /**
     * 리프레시 토큰을 저장하거나 업데이트합니다.
     *
     * @param refreshToken 저장 또는 갱신할 RefreshToken 엔티티
     * @return 저장된 RefreshToken 엔티티
     */
    RefreshToken save(final RefreshToken refreshToken);
}
