package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreType;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetScoreListUseCase {

    private final ScoreRepository scoreRepository;

    // 내 악보 목록 (WARMUP 제외)
    @Transactional(readOnly = true)
    public List<Score> execute(Long userId) {
        return scoreRepository.findByUserIdAndScoreTypeNot(userId, ScoreType.WARMUP);
    }
}