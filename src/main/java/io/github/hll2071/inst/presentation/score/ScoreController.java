package io.github.hll2071.inst.presentation.score;

import io.github.hll2071.inst.application.score.*;
import io.github.hll2071.inst.presentation.score.dto.request.CreateScoreByYoutubeRequest;
import io.github.hll2071.inst.presentation.score.dto.response.ScoreResponse;
import io.github.hll2071.inst.presentation.score.dto.response.ScoreStatusResponse;
import io.github.hll2071.inst.shared.enums.Instrument;
import io.github.hll2071.inst.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final CreateScoreByYoutubeUseCase createScoreByYoutubeUseCase;
    private final CreateScoreByFileUseCase createScoreByFileUseCase;
    private final GetScoreUseCase getScoreUseCase;
    private final GetScoreListUseCase getScoreListUseCase;
    private final GetScoreStatusUseCase getScoreStatusUseCase;
    private final GetWarmupScoreListUseCase getWarmupScoreListUseCase;

    // YouTube URL로 악보 생성 요청
    @PostMapping("/youtube")
    public ResponseEntity<ApiResponse<Long>> createByYoutube(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody CreateScoreByYoutubeRequest request) {
        CreateScoreByYoutubeUseCase.Result result = createScoreByYoutubeUseCase.execute(
                new CreateScoreByYoutubeUseCase.Command(
                        userId, request.youtubeUrl(), request.title(), request.instrument()));
        return ResponseEntity.accepted().body(ApiResponse.ok(result.scoreId()));
    }

    // 파일 업로드로 악보 생성
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Long>> createByFile(
            @AuthenticationPrincipal Long userId,
            @RequestParam String title,
            @RequestParam Instrument instrument,
            @RequestParam MultipartFile file) throws IOException {
        CreateScoreByFileUseCase.Result result = createScoreByFileUseCase.execute(
                new CreateScoreByFileUseCase.Command(userId, title, instrument, file));
        return ResponseEntity.ok(ApiResponse.ok(result.scoreId()));
    }

    // 악보 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScoreResponse>>> getScoreList(
            @AuthenticationPrincipal Long userId) {
        List<ScoreResponse> responses = getScoreListUseCase.execute(userId).stream()
                .map(ScoreResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    // 악보 단건 조회
    @GetMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<ScoreResponse>> getScore(
            @PathVariable Long scoreId) {
        return ResponseEntity.ok(ApiResponse.ok(ScoreResponse.from(getScoreUseCase.execute(scoreId))));
    }

    // 파이프라인 상태 폴링
    @GetMapping("/{scoreId}/status")
    public ResponseEntity<ApiResponse<ScoreStatusResponse>> getScoreStatus(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long scoreId) {
        return ResponseEntity.ok(ApiResponse.ok(
                ScoreStatusResponse.from(getScoreStatusUseCase.execute(userId, scoreId))));
    }

    // 손풀기 악보 목록
    @GetMapping("/warmup")
    public ResponseEntity<ApiResponse<List<ScoreResponse>>> getWarmupScores(
            @RequestParam Instrument instrument) {
        List<ScoreResponse> responses = getWarmupScoreListUseCase.execute(instrument).stream()
                .map(ScoreResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }
}