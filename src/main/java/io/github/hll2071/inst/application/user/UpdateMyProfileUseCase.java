package io.github.hll2071.inst.application.user;

import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateMyProfileUseCase {

    private final UserRepository userRepository;

    public record Command(Long userId, String nickname) {}

    @Transactional
    public void execute(Command command) {
        userRepository.findById(command.userId())
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND))
                .updateNickname(command.nickname());
    }
}