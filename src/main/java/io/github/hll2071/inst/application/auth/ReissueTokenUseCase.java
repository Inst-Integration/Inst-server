// application/auth/ReissueTokenUseCase.java
package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import io.github.hll2071.inst.shared.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReissueTokenUseCase {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public record Result(String accessToken, String refreshToken) {}

    public Result execute(String refreshToken) {
        if (!jwtProvider.validate(refreshToken)) {
            throw new InstException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtProvider.getUserId(refreshToken);

        String stored = tokenRepository.findRefreshToken(userId)
                .orElseThrow(() -> new InstException(ErrorCode.EXPIRED_REFRESH_TOKEN));

        if (!stored.equals(refreshToken)) {
            throw new InstException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtProvider.createAccessToken(userId, user.getRole().name());
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        tokenRepository.saveRefreshToken(userId, newRefreshToken, Duration.ofDays(7));

        return new Result(newAccessToken, newRefreshToken);
    }
}