package io.github.hll2071.inst.presentation.teacher.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateInquiryRequest(
        @NotBlank String message
) {}