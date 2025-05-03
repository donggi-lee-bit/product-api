package donggi.lee.catalog.user.domain;

public record Tokens(
    String accessToken,
    String refreshToken
) {
}
