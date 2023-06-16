package com.lost.common.domain.exception;

public class UnauthorizedException extends LostBusinessException {

    private static final String MESSAGE = "권한이 없습니다.";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
