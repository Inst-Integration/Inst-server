package io.github.hll2071.inst.infrastructure.content;

import io.github.hll2071.inst.domain.content.Content;
import io.github.hll2071.inst.domain.content.ContentLevel;
import io.github.hll2071.inst.domain.content.UserContentProgress;
import io.github.hll2071.inst.shared.enums.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByInstrumentAndLevelOrderByOrderIndex(Instrument instrument, ContentLevel level);
}