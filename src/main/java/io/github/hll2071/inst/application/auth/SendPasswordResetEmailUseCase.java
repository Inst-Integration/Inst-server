package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendPasswordResetEmailUseCase {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    public void execute(String email) {
        // 존재하지 않는 이메일이어도 동일한 응답 반환 (이메일 존재 여부 노출 방지)
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            tokenRepository.savePasswordResetToken(token, user.getId(), Duration.ofMinutes(30));

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[inst] 비밀번호 재설정");
            message.setText("http://13.209.8.219/auth/verify?token=" + token);
            mailSender.send(message);
        });
    }
}