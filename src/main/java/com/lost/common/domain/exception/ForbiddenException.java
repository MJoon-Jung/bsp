package com.lost.common.domain.exception;

public class ForbiddenException extends LostBusinessException {

    private static final String MESSAGE = "권한이 없습니다.";

    public ForbiddenException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 403;
    }
}
