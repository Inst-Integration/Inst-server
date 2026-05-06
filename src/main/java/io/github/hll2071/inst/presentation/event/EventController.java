package io.github.hll2071.inst.presentation.event;

import io.github.hll2071.inst.application.event.*;
import io.github.hll2071.inst.presentation.event.dto.response.EventResponse;
import io.github.hll2071.inst.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final GetEventListUseCase getEventListUseCase;
    private final GetEventDetailUseCase getEventDetailUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getEventList() {
        List<EventResponse> responses = getEventListUseCase.execute().stream()
                .map(EventResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventDetail(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(ApiResponse.ok(
                EventResponse.from(getEventDetailUseCase.execute(eventId))));
    }
}