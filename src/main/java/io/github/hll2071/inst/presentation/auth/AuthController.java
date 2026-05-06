package io.github.hll2071.inst.presentation.auth;

import io.github.hll2071.inst.application.auth.*;
import io.github.hll2071.inst.presentation.auth.dto.request.*;
import io.github.hll2071.inst.presentation.auth.dto.response.TokenResponse;
import io.github.hll2071.inst.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SendVerificationEmailUseCase sendVerificationEmailUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ReissueTokenUseCase reissueTokenUseCase;
    private final SendPasswordResetEmailUseCase sendPasswordResetEmailUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    // 1. 회원가입 - 이메일 인증 메일 발송 (email + password + nickname 한 번에)
    @PostMapping("/send-verification")
    public ResponseEntity<ApiResponse<Void>> sendVerification(
            @Valid @RequestBody SendVerificationRequest request) {
        sendVerificationEmailUseCase.execute(
                new SendVerificationEmailUseCase.Command(
                        request.email(), request.password(), request.nickname()));
        return ResponseEntity.ok(ApiResponse.ok("인증 메일을 발송했습니다.", null));
    }

    // 2. 이메일 인증 링크 클릭 → GET으로 User 생성 → 딥링크 리다이렉트
    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        VerifyEmailUseCase.Result result = verifyEmailUseCase.execute(token);
        String redirectUrl = "instapp://auth/verified"
                + "?accessToken=" + result.accessToken()
                + "&refreshToken=" + result.refreshToken();
        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

    // 3. 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        LoginUseCase.Result result = loginUseCase.execute(request.email(), request.password());
        return ResponseEntity.ok(ApiResponse.ok(TokenResponse.from(result)));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal Long userId) {
        logoutUseCase.execute(userId);
        return ResponseEntity.ok(ApiResponse.ok("로그아웃됐습니다.", null));
    }

    // 4. 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(
            @Valid @RequestBody ReissueRequest request) {
        ReissueTokenUseCase.Result result = reissueTokenUseCase.execute(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.ok(TokenResponse.from(result)));
    }

    // 5. 비밀번호 재설정 메일 발송
    @PostMapping("/send-password-reset")
    public ResponseEntity<ApiResponse<Void>> sendPasswordReset(
            @Valid @RequestBody SendPasswordResetRequest request) {
        sendPasswordResetEmailUseCase.execute(request.email());
        return ResponseEntity.ok(ApiResponse.ok("비밀번호 재설정 메일을 발송했습니다.", null));
    }

    // 6. 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request.token(), request.newPassword());
        return ResponseEntity.ok(ApiResponse.ok("비밀번호가 변경됐습니다.", null));
    }
}