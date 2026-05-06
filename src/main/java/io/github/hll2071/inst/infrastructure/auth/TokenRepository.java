package io.github.hll2071.inst.infrastructure.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private static final String REFRESH_TOKEN_PREFIX = "refresh:";
    private static final String EMAIL_TOKEN_PREFIX = "email:";
    private static final String PASSWORD_RESET_TOKEN_PREFIX = "password-reset:";

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(Long userId, String token, Duration ttl) {
        redisTemplate.opsForValue()
                .set(REFRESH_TOKEN_PREFIX + userId, token, ttl);
    }

    public Optional<String> findRefreshToken(Long userId) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId));
    }

    public void deleteRefreshToken(Long userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

    public void saveEmailToken(String token, String email, Duration ttl) {
        redisTemplate.opsForValue()
                .set(EMAIL_TOKEN_PREFIX + token, email, ttl);
    }

    public Optional<String> findEmailToken(String token) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(EMAIL_TOKEN_PREFIX + token));
    }

    public void deleteEmailToken(String token) {
        redisTemplate.delete(EMAIL_TOKEN_PREFIX + token);
    }

    public void savePasswordResetToken(String token, Long userId, Duration ttl) {
        redisTemplate.opsForValue()
                .set(PASSWORD_RESET_TOKEN_PREFIX + token, String.valueOf(userId), ttl);
    }

    public Optional<String> findPasswordResetToken(String token) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(PASSWORD_RESET_TOKEN_PREFIX + token));
    }

    public void deletePasswordResetToken(String token) {
        redisTemplate.delete(PASSWORD_RESET_TOKEN_PREFIX + token);
    }
}