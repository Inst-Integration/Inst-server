package io.github.hll2071.inst.presentation.tmi.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubmitQuizRequest(
        @NotNull Long optionId
) {}