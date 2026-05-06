package io.github.hll2071.inst.presentation.tmi.dto.request;

import io.github.hll2071.inst.domain.tmi.TmiCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SetTmiInterestRequest(
        @NotNull List<TmiCategory> categories
) {}