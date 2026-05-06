package io.github.hll2071.inst.infrastructure.event;

import io.github.hll2071.inst.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEndAtAfterOrderByStartAtDesc(LocalDateTime now);
}