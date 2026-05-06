package io.github.hll2071.inst.presentation.teacher;

import io.github.hll2071.inst.application.teacher.*;
import io.github.hll2071.inst.presentation.teacher.dto.request.CreateInquiryRequest;
import io.github.hll2071.inst.presentation.teacher.dto.response.TeacherResponse;
import io.github.hll2071.inst.shared.enums.Instrument;
import io.github.hll2071.inst.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final GetTeacherListUseCase getTeacherListUseCase;
    private final GetTeacherDetailUseCase getTeacherDetailUseCase;
    private final CreateTeacherInquiryUseCase createTeacherInquiryUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getTeacherList(
            @RequestParam(required = false) Instrument instrument) {
        List<TeacherResponse> responses = getTeacherListUseCase.execute(instrument).stream()
                .map(TeacherResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<ApiResponse<TeacherResponse>> getTeacherDetail(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.ok(
                TeacherResponse.from(getTeacherDetailUseCase.execute(teacherId))));
    }

    @PostMapping("/{teacherId}/inquiries")
    public ResponseEntity<ApiResponse<Void>> createInquiry(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long teacherId,
            @Valid @RequestBody CreateInquiryRequest request) {
        createTeacherInquiryUseCase.execute(
                new CreateTeacherInquiryUseCase.Command(userId, teacherId, request.message()));
        return ResponseEntity.ok(ApiResponse.ok("상담 신청이 완료됐습니다.", null));
    }
}