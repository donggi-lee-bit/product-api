package donggi.lee.catalog.user.controller;

import donggi.lee.catalog.user.application.AuthService;
import donggi.lee.catalog.user.controller.dto.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/signup")
@RequiredArgsConstructor
public class SignupRestController {

    private final AuthService authService;

    @PostMapping
    public void signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request.email(), request.rawPassword());
    }
}
