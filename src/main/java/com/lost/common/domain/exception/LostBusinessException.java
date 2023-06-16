package com.lost.common.domain.exception;

public class LostBusinessException extends RuntimeException {

    public LostBusinessException() {
    }

    public LostBusinessException(String message) {
        super(message);
    }

    public LostBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LostBusinessException(Throwable cause) {
        super(cause);
    }

    protected LostBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
