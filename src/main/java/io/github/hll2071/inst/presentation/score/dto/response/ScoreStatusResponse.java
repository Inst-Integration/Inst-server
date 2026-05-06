package io.github.hll2071.inst.presentation.score.dto.response;

import io.github.hll2071.inst.application.score.GetScoreStatusUseCase;
import io.github.hll2071.inst.domain.score.ScoreStatus;

public record ScoreStatusResponse(
        Long scoreId,
        ScoreStatus status,
        String musicXmlUrl
) {
    public static ScoreStatusResponse from(GetScoreStatusUseCase.Result result) {
        return new ScoreStatusResponse(result.scoreId(), result.status(), result.musicXmlUrl());
    }
}