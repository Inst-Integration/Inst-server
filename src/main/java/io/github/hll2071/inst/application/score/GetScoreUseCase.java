package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetScoreUseCase {

    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public Score execute(Long scoreId) {
        return scoreRepository.findById(scoreId)
                .orElseThrow(() -> new InstException(ErrorCode.SCORE_NOT_FOUND));
    }
}