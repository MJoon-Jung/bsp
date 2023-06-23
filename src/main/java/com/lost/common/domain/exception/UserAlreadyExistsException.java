package com.lost.common.domain.exception;

public class UserAlreadyExistsException extends LostBusinessException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
