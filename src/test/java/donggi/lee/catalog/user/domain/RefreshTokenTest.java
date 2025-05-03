package donggi.lee.catalog.user.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RefreshTokenTest {
    @Test
    void 토큰을_업데이트하면_최신_token과_만료시간으로_변경된다() {
        // given
        final var originalExpiration = LocalDateTime.now().plusHours(1);
        final var user = new User("user@example.com", "password123");
        final var refreshToken = new RefreshToken("initial-token", originalExpiration, user);
        final var newTokenValue = "updated-token";
        final var newExpirationTime = LocalDateTime.now().plusDays(1);

        // when
        refreshToken.updateToken(newTokenValue, newExpirationTime);

        // then
        assertThat(refreshToken.getToken()).isEqualTo(newTokenValue);
        assertThat(refreshToken.getExpiresAt()).isEqualTo(newExpirationTime);
    }

    @Test
    void 만료시간이_이미_지난_RefreshToken은_isExpired가_true를_반환한다() {
        // given
        final var pastExpiration = LocalDateTime.now().minusHours(1);
        final var user = new User("user@example.com", "password123");
        final var refreshToken = new RefreshToken("token", pastExpiration, user);

        // when
        final var actual = refreshToken.isExpired();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 만료시간이_미래인_RefreshToken은_isExpired가_false를_반환한다() {
        // given
        final var futureExpiration = LocalDateTime.now().plusHours(1);
        final var user = new User("user@example.com", "password123");
        final var refreshToken = new RefreshToken("token", futureExpiration, user);

        // when
        final var actual = refreshToken.isExpired();

        // then
        assertThat(actual).isFalse();
    }
}
