package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.domain.user.UserRole;
import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import io.github.hll2071.inst.shared.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class VerifyEmailUseCase {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;

    public record Result(String accessToken, String refreshToken) {}

    @Transactional
    public Result execute(String token) {
        String payload = tokenRepository.findEmailToken(token)
                .orElseThrow(() -> new InstException(ErrorCode.INVALID_EMAIL_TOKEN));

        SendVerificationEmailUseCase.Command command;
        try {
            command = objectMapper.readValue(payload,
                    SendVerificationEmailUseCase.Command.class);
        } catch (Exception e) {
            throw new InstException(ErrorCode.INVALID_EMAIL_TOKEN);
        }

        User user = User.builder()
                .email(command.email())
                .password(passwordEncoder.encode(command.password()))
                .nickname(command.nickname())
                .role(UserRole.USER)
                .emailVerified(true)
                .build();

        userRepository.save(user);
        tokenRepository.deleteEmailToken(token);

        // 회원 생성 후 즉시 JWT 발급
        String accessToken = jwtProvider.createAccessToken(
                user.getId(), user.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        tokenRepository.saveRefreshToken(user.getId(), refreshToken, Duration.ofDays(7));

        return new Result(accessToken, refreshToken);
    }
}