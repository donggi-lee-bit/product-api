package donggi.lee.catalog.user.controller;

import donggi.lee.catalog.user.application.AuthService;
import donggi.lee.catalog.user.controller.dto.LoginRequest;
import donggi.lee.catalog.user.controller.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
public class LoginRestController {

    private final AuthService authService;

    @PostMapping
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.email(), request.rawPassword());
    }
}
