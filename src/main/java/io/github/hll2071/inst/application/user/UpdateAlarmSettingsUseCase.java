package io.github.hll2071.inst.application.user;

import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAlarmSettingsUseCase {

    private final UserRepository userRepository;

    public record Command(
            Long userId,
            boolean practiceAlarm,
            boolean eventAlarm,
            boolean contentAlarm
    ) {}

    @Transactional
    public void execute(Command command) {
        userRepository.findById(command.userId())
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND))
                .updateAlarmSettings(
                        command.practiceAlarm(),
                        command.eventAlarm(),
                        command.contentAlarm()
                );
    }
}