package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreStatus;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetScoreStatusUseCase {

    private final ScoreRepository scoreRepository;

    public record Result(Long scoreId, ScoreStatus status, String musicXmlUrl) {}

    @Transactional(readOnly = true)
    public Result execute(Long userId, Long scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new InstException(ErrorCode.SCORE_NOT_FOUND));

        // 본인 악보가 아닌 경우
        if (!score.getUser().getId().equals(userId)) {
            throw new InstException(ErrorCode.FORBIDDEN);
        }

        return new Result(score.getId(), score.getStatus(), score.getMusicXmlUrl());
    }
}