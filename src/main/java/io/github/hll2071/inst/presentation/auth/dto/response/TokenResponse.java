package io.github.hll2071.inst.presentation.auth.dto.response;

import io.github.hll2071.inst.application.auth.LoginUseCase;
import io.github.hll2071.inst.application.auth.ReissueTokenUseCase;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenResponse from(LoginUseCase.Result result) {
        return new TokenResponse(result.accessToken(), result.refreshToken());
    }

    public static TokenResponse from(ReissueTokenUseCase.Result result) {
        return new TokenResponse(result.accessToken(), result.refreshToken());
    }
}