package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(String token, String newPassword) {
        Long userId = tokenRepository.findPasswordResetToken(token)
                .map(Long::parseLong)
                .orElseThrow(() -> new InstException(ErrorCode.PASSWORD_RESET_TOKEN_NOT_FOUND));

        userRepository.findById(userId)
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND))
                .updatePassword(passwordEncoder.encode(newPassword));

        tokenRepository.deletePasswordResetToken(token);
    }
}