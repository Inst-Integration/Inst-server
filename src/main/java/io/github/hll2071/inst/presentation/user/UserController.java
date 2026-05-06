package io.github.hll2071.inst.presentation.user;

import io.github.hll2071.inst.application.practice.GetPracticeListUseCase;
import io.github.hll2071.inst.application.user.*;
import io.github.hll2071.inst.presentation.practice.dto.response.PracticeResponse;
import io.github.hll2071.inst.presentation.user.dto.request.UpdateAlarmSettingsRequest;
import io.github.hll2071.inst.presentation.user.dto.request.UpdateProfileRequest;
import io.github.hll2071.inst.presentation.user.dto.response.MyProfileResponse;
import io.github.hll2071.inst.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GetMyProfileUseCase getMyProfileUseCase;
    private final UpdateMyProfileUseCase updateMyProfileUseCase;
    private final DeleteMyAccountUseCase deleteMyAccountUseCase;
    private final GetPracticeListUseCase getPracticeListUseCase;
    private final UpdateAlarmSettingsUseCase updateAlarmSettingsUseCase;

    // 내 프로필 + 학습 통계
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MyProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(
                MyProfileResponse.from(getMyProfileUseCase.execute(userId))));
    }

    // 프로필 수정
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateMyProfile(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        updateMyProfileUseCase.execute(
                new UpdateMyProfileUseCase.Command(userId, request.nickname()));
        return ResponseEntity.ok(ApiResponse.ok("프로필이 수정됐습니다.", null));
    }

    // 연주 기록 목록 (마이페이지)
    @GetMapping("/me/practices")
    public ResponseEntity<ApiResponse<List<PracticeResponse>>> getMyPractices(
            @AuthenticationPrincipal Long userId) {
        List<PracticeResponse> responses = getPracticeListUseCase.execute(userId).stream()
                .map(PracticeResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMyAccount(
            @AuthenticationPrincipal Long userId) {
        deleteMyAccountUseCase.execute(userId);
        return ResponseEntity.ok(ApiResponse.ok("회원 탈퇴가 완료됐습니다.", null));
    }

    // 알림 설정
    @PatchMapping("/me/alarm-settings")
    public ResponseEntity<ApiResponse<Void>> updateAlarmSettings(
            @AuthenticationPrincipal Long userId,
            @RequestBody UpdateAlarmSettingsRequest request) {
        updateAlarmSettingsUseCase.execute(
                new UpdateAlarmSettingsUseCase.Command(
                        userId,
                        request.practiceAlarm(),
                        request.eventAlarm(),
                        request.contentAlarm()
                ));
        return ResponseEntity.ok(ApiResponse.ok("알림 설정이 변경됐습니다.", null));
    }
}