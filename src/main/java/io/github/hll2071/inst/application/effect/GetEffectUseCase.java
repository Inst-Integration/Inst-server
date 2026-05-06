package io.github.hll2071.inst.application.effect;

import io.github.hll2071.inst.domain.effect.Effect;
import io.github.hll2071.inst.infrastructure.effect.EffectRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetEffectUseCase {

    private final EffectRepository effectRepository;

    @Transactional(readOnly = true)
    public Effect execute(Long effectId) {
        return effectRepository.findById(effectId)
                .orElseThrow(() -> new InstException(ErrorCode.EFFECT_NOT_FOUND));
    }
}