package io.github.hll2071.inst.presentation.tmi;

import io.github.hll2071.inst.application.tmi.*;
import io.github.hll2071.inst.presentation.tmi.dto.request.SetTmiInterestRequest;
import io.github.hll2071.inst.presentation.tmi.dto.request.SubmitQuizRequest;
import io.github.hll2071.inst.presentation.tmi.dto.response.TmiDetailResponse;
import io.github.hll2071.inst.presentation.tmi.dto.response.TmiResponse;
import io.github.hll2071.inst.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tmis")
@RequiredArgsConstructor
public class TmiController {

    private final GetTmiListUseCase getTmiListUseCase;
    private final GetTmiDetailUseCase getTmiDetailUseCase;
    private final SetTmiInterestUseCase setTmiInterestUseCase;
    private final SubmitTmiQuizUseCase submitTmiQuizUseCase;

    // 오늘의 TMI 목록 (관심사 기반 랜덤 3개)
    @GetMapping
    public ResponseEntity<ApiResponse<List<TmiResponse>>> getTmiList(
            @AuthenticationPrincipal Long userId) {
        List<TmiResponse> responses = getTmiListUseCase.execute(userId).stream()
                .map(TmiResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    // TMI 상세
    @GetMapping("/{tmiId}")
    public ResponseEntity<ApiResponse<TmiDetailResponse>> getTmiDetail(
            @PathVariable Long tmiId) {
        GetTmiDetailUseCase.Result result = getTmiDetailUseCase.execute(tmiId);
        return ResponseEntity.ok(ApiResponse.ok(
                TmiDetailResponse.from(result.tmi(), result.quizzes())));
    }

    // 관심사 설정
    @PostMapping("/interests")
    public ResponseEntity<ApiResponse<Void>> setInterests(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody SetTmiInterestRequest request) {
        setTmiInterestUseCase.execute(userId, request.categories());
        return ResponseEntity.ok(ApiResponse.ok("관심사가 설정됐습니다.", null));
    }

    // 퀴즈 제출
    @PostMapping("/quizzes/{quizId}/submit")
    public ResponseEntity<ApiResponse<Boolean>> submitQuiz(
            @PathVariable Long quizId,
            @Valid @RequestBody SubmitQuizRequest request) {
        SubmitTmiQuizUseCase.Result result = submitTmiQuizUseCase.execute(quizId, request.optionId());
        return ResponseEntity.ok(ApiResponse.ok(result.correct()));
    }
}