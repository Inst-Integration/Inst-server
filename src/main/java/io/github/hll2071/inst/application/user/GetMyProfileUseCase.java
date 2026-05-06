package io.github.hll2071.inst.application.user;

import io.github.hll2071.inst.domain.content.UserContentProgress;
import io.github.hll2071.inst.domain.practice.Practice;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.content.UserContentProgressRepository;
import io.github.hll2071.inst.infrastructure.practice.PracticeRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class GetMyProfileUseCase {

    private final UserRepository userRepository;
    private final PracticeRepository practiceRepository;
    private final UserContentProgressRepository userContentProgressRepository;

    public record Stats(
            int totalPracticeDays,
            int totalPracticeCount,
            int averageScore
    ) {}

    public record Result(User user, Stats stats) {}

    @Transactional(readOnly = true)
    public Result execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        List<Practice> practices = practiceRepository.findByUserIdOrderByCreatedAtDesc(userId);

        // 연주한 날짜 중 고유한 날짜 수
        int totalPracticeDays = (int) practices.stream()
                .map(p -> p.getCreatedAt().toLocalDate())
                .distinct()
                .count();

        int totalPracticeCount = practices.size();

        int averageScore = (int) practices.stream()
                .mapToInt(Practice::getTotalScore)
                .average()
                .orElse(0);

        return new Result(user, new Stats(totalPracticeDays, totalPracticeCount, averageScore));
    }
}