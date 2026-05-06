package io.github.hll2071.inst.presentation.effect.dto.response;

import io.github.hll2071.inst.domain.effect.Effect;
import io.github.hll2071.inst.shared.enums.Instrument;

public record EffectResponse(
        Long id,
        String name,
        String description,
        Instrument instrument,
        String previewAudioUrl,
        String brandName
) {
    public static EffectResponse from(Effect effect) {
        return new EffectResponse(
                effect.getId(),
                effect.getName(),
                effect.getDescription(),
                effect.getInstrument(),
                effect.getPreviewAudioUrl(),
                effect.getBrandName()
        );
    }
}