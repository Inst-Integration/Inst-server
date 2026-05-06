package io.github.hll2071.inst.presentation.user.dto.response;

import io.github.hll2071.inst.application.user.GetMyProfileUseCase;
import io.github.hll2071.inst.domain.user.User;

import java.time.LocalDateTime;

public record MyProfileResponse(
        Long id,
        String email,
        String nickname,
        LocalDateTime createdAt,
        boolean practiceAlarm,
        boolean eventAlarm,
        boolean contentAlarm,
        StatsResponse stats
) {
    public record StatsResponse(
            int totalPracticeDays,
            int totalPracticeCount,
            int averageScore
    ) {
        public static StatsResponse from(GetMyProfileUseCase.Stats stats) {
            return new StatsResponse(
                    stats.totalPracticeDays(),
                    stats.totalPracticeCount(),
                    stats.averageScore()
            );
        }
    }

    public static MyProfileResponse from(GetMyProfileUseCase.Result result) {
        User user = result.user();
        return new MyProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getCreatedAt(),
                user.isPracticeAlarm(),
                user.isEventAlarm(),
                user.isContentAlarm(),
                StatsResponse.from(result.stats())
        );
    }
}