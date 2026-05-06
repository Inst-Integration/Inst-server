// presentation/event/dto/response/EventResponse.java
package io.github.hll2071.inst.presentation.event.dto.response;

import io.github.hll2071.inst.domain.event.Event;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        String imageUrl,
        String linkUrl,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getImageUrl(),
                event.getLinkUrl(),
                event.getStartAt(),
                event.getEndAt()
        );
    }
}