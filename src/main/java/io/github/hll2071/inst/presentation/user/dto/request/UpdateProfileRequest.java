package io.github.hll2071.inst.presentation.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
        @NotBlank String nickname
) {}