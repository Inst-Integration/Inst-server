package io.github.hll2071.inst.presentation.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendPasswordResetRequest(
        @NotBlank @Email String email
) {}