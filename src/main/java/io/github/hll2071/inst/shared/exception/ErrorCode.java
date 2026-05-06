package io.github.hll2071.inst.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),

    // Auth
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, "이메일 인증이 필요합니다."),
    INVALID_EMAIL_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 토큰입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 Refresh Token입니다."),
    PASSWORD_RESET_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호 재설정 토큰입니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // Score
    SCORE_NOT_FOUND(HttpStatus.NOT_FOUND, "악보를 찾을 수 없습니다."),
    SCORE_PIPELINE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "악보 생성 중 오류가 발생했습니다."),

    // Tmi
    TMI_NOT_FOUND(HttpStatus.NOT_FOUND, "TMI를 찾을 수 없습니다."),
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "퀴즈를 찾을 수 없습니다."),

    // Content
    CONTENT_COMPLETION_SCORE_NOT_MET(HttpStatus.BAD_REQUEST, "수료 조건 점수를 충족하지 못했습니다."),

    // Event, Teacher, Effect
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "이벤트를 찾을 수 없습니다."),
    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND, "강사를 찾을 수 없습니다."),
    EFFECT_NOT_FOUND(HttpStatus.NOT_FOUND, "이펙터를 찾을 수 없습니다."),

    // Practice
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "학습 콘텐츠를 찾을 수 없습니다."),
    PRACTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "연주 기록을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}