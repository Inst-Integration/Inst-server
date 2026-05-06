package io.github.hll2071.inst.application.event;

import io.github.hll2071.inst.domain.event.Event;
import io.github.hll2071.inst.infrastructure.event.EventRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetEventDetailUseCase {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public Event execute(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new InstException(ErrorCode.EVENT_NOT_FOUND));
    }
}