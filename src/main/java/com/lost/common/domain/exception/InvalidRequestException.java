package com.lost.common.domain.exception;

public class InvalidRequestException extends LostBusinessException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequestException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
