package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreType;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.shared.enums.Instrument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetWarmupScoreListUseCase {

    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public List<Score> execute(Instrument instrument) {
        return scoreRepository.findByScoreTypeAndInstrument(ScoreType.WARMUP, instrument);
    }
}