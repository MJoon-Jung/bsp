package com.lost.common.domain.exception;

public class MemberNotFoundException extends LostBusinessException {

    private static final String MESSAGE = "대화 상대가 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
