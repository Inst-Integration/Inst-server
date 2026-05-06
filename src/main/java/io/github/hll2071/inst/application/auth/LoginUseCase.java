package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import io.github.hll2071.inst.shared.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public record Result(String accessToken, String refreshToken) {}

    public Result execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InstException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InstException(ErrorCode.INVALID_CREDENTIALS);
        }

        if (!user.isEmailVerified()) {
            throw new InstException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        tokenRepository.saveRefreshToken(user.getId(), refreshToken, Duration.ofDays(7));

        return new Result(accessToken, refreshToken);
    }
}