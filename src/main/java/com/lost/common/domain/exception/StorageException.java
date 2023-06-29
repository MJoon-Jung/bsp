package com.lost.common.domain.exception;

public class StorageException extends LostBusinessException {

    public StorageException(String message) {
        super(message);
    }


    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public int statusCode() {
        return 500;
    }
}
