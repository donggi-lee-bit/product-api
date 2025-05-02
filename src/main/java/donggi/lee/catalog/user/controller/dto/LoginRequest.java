package donggi.lee.catalog.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email(message = "올바른 이메일 형식을 입력하세요")
    String email,

    @NotBlank(message = "비밀번호는 필수 입력입니다")
    String rawPassword
) {
}
