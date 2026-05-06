package io.github.hll2071.inst.application.auth;

import io.github.hll2071.inst.infrastructure.auth.TokenRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendVerificationEmailUseCase {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    // Redis에 임시 저장할 회원가입 데이터
    public record Command(String email, String password, String nickname) {}

    public void execute(Command command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new InstException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String token = UUID.randomUUID().toString();

        try {
            String payload = objectMapper.writeValueAsString(command);
            tokenRepository.saveEmailToken(token, payload, Duration.ofMinutes(30));
        } catch (Exception e) {
            throw new InstException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(command.email());
        message.setSubject("[inst] 이메일 인증");
        message.setText(baseUrl + "/auth/verify-email?token=" + token);
        mailSender.send(message);
    }
}