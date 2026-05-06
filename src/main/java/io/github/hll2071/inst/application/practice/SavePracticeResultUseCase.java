package io.github.hll2071.inst.application.practice;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.domain.practice.IssueCategory;
import io.github.hll2071.inst.domain.practice.Practice;
import io.github.hll2071.inst.domain.practice.PracticeIssue;
import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.content.ContentRepository;
import io.github.hll2071.inst.infrastructure.practice.PracticeRepository;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavePracticeResultUseCase {

    private final PracticeRepository practiceRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final ContentRepository contentRepository;

    public record IssueCommand(
            int timestampSeconds,
            String message,
            IssueCategory category
    ) {}

    public record Command(
            Long userId,
            Long scoreId,        // 일반 연주 시
            Long contentId,      // 학습 콘텐츠 연주 시
            int pitchScore,
            int rhythmScore,
            int postureScore,
            int totalScore,
            int durationSeconds,
            List<IssueCommand> issues
    ) {}

    public record Result(Long practiceId) {}

    @Transactional
    public Result execute(Command command) {
        if (command.scoreId() == null && command.contentId() == null) {
            throw new InstException(ErrorCode.INVALID_INPUT);
        }

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        Score score = command.scoreId() != null
                ? scoreRepository.findById(command.scoreId())
                .orElseThrow(() -> new InstException(ErrorCode.SCORE_NOT_FOUND))
                : null;

        Content content = command.contentId() != null
                ? contentRepository.findById(command.contentId())
                .orElseThrow(() -> new InstException(ErrorCode.CONTENT_NOT_FOUND))
                : null;

        Practice practice = Practice.builder()
                .user(user)
                .score(score)
                .content(content)
                .pitchScore(command.pitchScore())
                .rhythmScore(command.rhythmScore())
                .postureScore(command.postureScore())
                .totalScore(command.totalScore())
                .durationSeconds(command.durationSeconds())
                .build();

        command.issues().forEach(i -> practice.addIssue(
                PracticeIssue.builder()
                        .practice(practice)
                        .timestampSeconds(i.timestampSeconds())
                        .message(i.message())
                        .category(i.category())
                        .build()
        ));

        practiceRepository.save(practice);
        return new Result(practice.getId());
    }
}