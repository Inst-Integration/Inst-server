package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreStatus;
import io.github.hll2071.inst.domain.score.ScoreType;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.enums.Instrument;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateScoreByYoutubeUseCase {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final ScorePipelineRunner scorePipelineRunner;

    public record Command(Long userId, String youtubeUrl, String title, Instrument instrument) {
    }

    public record Result(Long scoreId) {
    }

    // 1. 즉시 PENDING 상태로 저장 후 scoreId 반환
    @Transactional
    public Result execute(Command command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        Score score = Score.builder()
                .user(user)
                .title(command.title())
                .scoreType(ScoreType.AUTO)
                .instrument(command.instrument())
                .status(ScoreStatus.PENDING)
                .youtubeUrl(command.youtubeUrl())
                .build();

        scoreRepository.save(score);
        Long scoreId = score.getId();
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        scorePipelineRunner.run(scoreId, command.youtubeUrl(), command.instrument().name());
                    }
                }
        );

        return new Result(score.getId());
    }
}