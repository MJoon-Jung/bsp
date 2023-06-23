package com.lost.common.domain.exception;

public abstract class LostBusinessException extends RuntimeException {

    public LostBusinessException(String message) {
        super(message);
    }


    public abstract int statusCode();
}
