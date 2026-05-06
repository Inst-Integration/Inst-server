package io.github.hll2071.inst.presentation.effect;

import io.github.hll2071.inst.application.effect.*;
import io.github.hll2071.inst.presentation.effect.dto.response.EffectResponse;
import io.github.hll2071.inst.shared.enums.Instrument;
import io.github.hll2071.inst.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/effects")
@RequiredArgsConstructor
public class EffectController {

    private final GetEffectListUseCase getEffectListUseCase;
    private final GetEffectUseCase getEffectUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EffectResponse>>> getEffectList(
            @RequestParam Instrument instrument) {
        List<EffectResponse> responses = getEffectListUseCase.execute(instrument).stream()
                .map(EffectResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    @GetMapping("/{effectId}")
    public ResponseEntity<ApiResponse<EffectResponse>> getEffect(
            @PathVariable Long effectId) {
        return ResponseEntity.ok(ApiResponse.ok(
                EffectResponse.from(getEffectUseCase.execute(effectId))));
    }
}