package io.github.hll2071.inst.application.user;

import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteMyAccountUseCase {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    public void execute(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        tokenRepository.deleteRefreshToken(userId);
        userRepository.deleteById(userId);
    }
}