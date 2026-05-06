// infrastructure/score/ScoreRepository.java
package io.github.hll2071.inst.infrastructure.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreStatus;
import io.github.hll2071.inst.domain.score.ScoreType;
import io.github.hll2071.inst.shared.enums.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByUserIdAndScoreTypeNot(Long userId, ScoreType scoreType);
    List<Score> findByScoreTypeAndInstrument(ScoreType scoreType, Instrument instrument);
    List<Score> findByUserIdAndStatus(Long userId, ScoreStatus status);
}