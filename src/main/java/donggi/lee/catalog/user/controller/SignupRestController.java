package donggi.lee.catalog.user.controller;

import donggi.lee.catalog.user.application.AuthService;
import donggi.lee.catalog.user.controller.dto.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "회원가입 관련 API")
@RestController
@RequestMapping("/v1/signup")
@RequiredArgsConstructor
public class SignupRestController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호로 새 계정을 생성합니다")
    @PostMapping
    public void signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request.email(), request.rawPassword());
    }
}
