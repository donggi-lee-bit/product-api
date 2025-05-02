package donggi.lee.catalog.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
    @Email String email,
    @NotBlank String rawPassword
) { }
