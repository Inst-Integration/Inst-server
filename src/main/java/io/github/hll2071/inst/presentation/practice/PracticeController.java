package io.github.hll2071.inst.presentation.practice;

import io.github.hll2071.inst.application.practice.*;
import io.github.hll2071.inst.presentation.practice.dto.request.SavePracticeResultRequest;
import io.github.hll2071.inst.presentation.practice.dto.response.PracticeResponse;
import io.github.hll2071.inst.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/practices")
@RequiredArgsConstructor
public class PracticeController {

    private final SavePracticeResultUseCase savePracticeResultUseCase;
    private final GetPracticeListUseCase getPracticeListUseCase;
    private final GetPracticeDetailUseCase getPracticeDetailUseCase;

    // 연주 결과 저장
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> savePracticeResult(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody SavePracticeResultRequest request) {
        SavePracticeResultUseCase.Result result = savePracticeResultUseCase.execute(
                new SavePracticeResultUseCase.Command(
                        userId,
                        request.scoreId(),
                        request.contentId(),
                        request.pitchScore(),
                        request.rhythmScore(),
                        request.postureScore(),
                        request.totalScore(),
                        request.durationSeconds(),
                        request.toIssueCommands()
                ));
        return ResponseEntity.ok(ApiResponse.ok(result.practiceId()));
    }

    // 연주 기록 목록
    @GetMapping
    public ResponseEntity<ApiResponse<List<PracticeResponse>>> getPracticeList(
            @AuthenticationPrincipal Long userId) {
        List<PracticeResponse> responses = getPracticeListUseCase.execute(userId).stream()
                .map(PracticeResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    // 연주 기록 상세
    @GetMapping("/{practiceId}")
    public ResponseEntity<ApiResponse<PracticeResponse>> getPracticeDetail(
            @PathVariable Long practiceId) {
        return ResponseEntity.ok(ApiResponse.ok(
                PracticeResponse.from(getPracticeDetailUseCase.execute(practiceId))));
    }
}