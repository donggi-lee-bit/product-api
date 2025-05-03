package donggi.lee.catalog.user.controller;

import donggi.lee.catalog.user.application.AuthService;
import donggi.lee.catalog.user.controller.dto.LoginRequest;
import donggi.lee.catalog.user.controller.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "로그인 관련 API")
@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
public class LoginRestController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인하고 토큰을 반환합니다")
    @PostMapping
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.email(), request.rawPassword());
    }
}
