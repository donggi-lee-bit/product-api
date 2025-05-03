package donggi.lee.catalog.user.application;

import donggi.lee.catalog.common.security.JwtTokenProvider;
import donggi.lee.catalog.user.controller.dto.LoginResponse;
import donggi.lee.catalog.user.domain.RefreshToken;
import donggi.lee.catalog.user.domain.Tokens;
import donggi.lee.catalog.user.domain.User;
import donggi.lee.catalog.user.domain.repository.RefreshTokenRepository;
import donggi.lee.catalog.user.domain.repository.UserRepository;
import donggi.lee.catalog.user.exception.IncorrectPasswordException;
import donggi.lee.catalog.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void signup(final String email, final String rawPassword) {
        // TODO: 이메일 중복 확인

        final var user = new User(email, passwordEncoder.encode(rawPassword));

        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(final String email, final String rawPassword) {
        final var user = userRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);

        verifyPassword(user.getEncodedPassword(), rawPassword);

        Tokens tokens = tokenService.issueTokensFor(user);

        return new LoginResponse(tokens.accessToken(), tokens.refreshToken());
    }

    private void verifyPassword(final String encodedPassword, final String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IncorrectPasswordException();
        }
    }
}
