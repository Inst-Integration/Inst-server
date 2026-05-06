package io.github.hll2071.inst.application.effect;

import io.github.hll2071.inst.domain.effect.Effect;
import io.github.hll2071.inst.infrastructure.effect.EffectRepository;
import io.github.hll2071.inst.shared.enums.Instrument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetEffectListUseCase {

    private final EffectRepository effectRepository;

    @Transactional(readOnly = true)
    public List<Effect> execute(Instrument instrument) {
        return effectRepository.findByInstrumentOrderByBrandName(instrument);
    }
}