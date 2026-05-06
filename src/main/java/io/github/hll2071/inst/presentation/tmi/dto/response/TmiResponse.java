package io.github.hll2071.inst.presentation.tmi.dto.response;

import io.github.hll2071.inst.domain.tmi.Tmi;
import io.github.hll2071.inst.domain.tmi.TmiCategory;

public record TmiResponse(
        Long id,
        String title,
        String content,
        TmiCategory category
) {
    public static TmiResponse from(Tmi tmi) {
        return new TmiResponse(
                tmi.getId(),
                tmi.getTitle(),
                tmi.getContent(),
                tmi.getCategory()
        );
    }
}