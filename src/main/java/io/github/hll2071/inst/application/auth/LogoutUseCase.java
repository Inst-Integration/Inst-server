package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final TokenRepository tokenRepository;

    public void execute(Long userId) {
        tokenRepository.deleteRefreshToken(userId);
    }
}