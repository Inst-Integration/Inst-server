package io.github.hll2071.inst.infrastructure.effect;

import io.github.hll2071.inst.domain.effect.Effect;
import io.github.hll2071.inst.shared.enums.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EffectRepository extends JpaRepository<Effect, Long> {
    List<Effect> findByInstrumentOrderByBrandName(Instrument instrument);
}