package io.github.hll2071.inst.shared.exception;

import lombok.Getter;

@Getter
public class InstException extends RuntimeException {

    private final ErrorCode errorCode;

    public InstException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}