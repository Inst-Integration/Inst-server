package io.github.hll2071.inst.presentation.content;

import io.github.hll2071.inst.application.content.*;
import io.github.hll2071.inst.domain.content.ContentLevel;
import io.github.hll2071.inst.presentation.content.dto.response.ContentResponse;
import io.github.hll2071.inst.shared.enums.Instrument;
import io.github.hll2071.inst.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentController {

    private final GetContentListUseCase getContentListUseCase;
    private final GetContentDetailUseCase getContentDetailUseCase;
    private final CompleteContentUseCase completeContentUseCase;

    // 학습 콘텐츠 목록 (악기 + 단계별)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getContentList(
            @AuthenticationPrincipal Long userId,
            @RequestParam Instrument instrument,
            @RequestParam ContentLevel level) {
        List<ContentResponse> responses = getContentListUseCase.execute(userId, instrument, level)
                .stream()
                .map(item -> ContentResponse.from(item.content(), item.completed()))
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    // 학습 콘텐츠 상세
    @GetMapping("/{contentId}")
    public ResponseEntity<ApiResponse<ContentResponse>> getContentDetail(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long contentId) {
        GetContentDetailUseCase.Result result = getContentDetailUseCase.execute(userId, contentId);
        return ResponseEntity.ok(ApiResponse.ok(
                ContentResponse.from(result.content(), result.completed())));
    }

    // 수료 처리
    @PostMapping("/{contentId}/complete")
    public ResponseEntity<ApiResponse<Void>> completeContent(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long contentId,
            @RequestParam int totalScore) {
        completeContentUseCase.execute(userId, contentId, totalScore);
        return ResponseEntity.ok(ApiResponse.ok("수료가 완료됐습니다.", null));
    }
}