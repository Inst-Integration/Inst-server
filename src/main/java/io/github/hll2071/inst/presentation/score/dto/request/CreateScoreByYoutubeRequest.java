package io.github.hll2071.inst.presentation.score.dto.request;

import io.github.hll2071.inst.shared.enums.Instrument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateScoreByYoutubeRequest(
        @NotBlank String youtubeUrl,
        @NotBlank String title,
        @NotNull Instrument instrument
) {}