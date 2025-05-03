package donggi.lee.catalog.user.application;

import donggi.lee.catalog.common.security.JwtTokenProvider;
import donggi.lee.catalog.user.domain.RefreshToken;
import donggi.lee.catalog.user.domain.Tokens;
import donggi.lee.catalog.user.domain.User;
import donggi.lee.catalog.user.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Tokens issueTokensFor(final User user) {
        final var accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        final var refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
        final var now = LocalDateTime.now();
        final var expiresAt = now.plus(Duration.ofMillis(jwtTokenProvider.getRefreshTokenExpirationMs()));

        final RefreshToken storedToken = refreshTokenRepository.findByUserId(user.getId())
            .orElse(new RefreshToken(refreshToken, expiresAt, user));

        storedToken.updateToken(refreshToken, expiresAt);

        refreshTokenRepository.save(storedToken);

        return new Tokens(accessToken, refreshToken);
    }
}
