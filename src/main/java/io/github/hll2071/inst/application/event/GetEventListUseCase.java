package io.github.hll2071.inst.application.event;

import io.github.hll2071.inst.domain.event.Event;
import io.github.hll2071.inst.infrastructure.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetEventListUseCase {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<Event> execute() {
        return eventRepository.findByEndAtAfterOrderByStartAtDesc(LocalDateTime.now());
    }
}