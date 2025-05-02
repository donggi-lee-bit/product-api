package donggi.lee.catalog.user.controller.dto;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {
}
