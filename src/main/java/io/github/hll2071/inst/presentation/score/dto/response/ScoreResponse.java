package io.github.hll2071.inst.presentation.score.dto.response;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreStatus;
import io.github.hll2071.inst.domain.score.ScoreType;
import io.github.hll2071.inst.shared.enums.Instrument;

import java.time.LocalDateTime;

public record ScoreResponse(
        Long id,
        String title,
        ScoreType scoreType,
        Instrument instrument,
        ScoreStatus status,
        String musicXmlUrl,
        LocalDateTime createdAt
) {
    public static ScoreResponse from(Score score) {
        return new ScoreResponse(
                score.getId(),
                score.getTitle(),
                score.getScoreType(),
                score.getInstrument(),
                score.getStatus(),
                score.getMusicXmlUrl(),
                score.getCreatedAt()
        );
    }
}