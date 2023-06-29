package com.lost.common.domain.exception;

public class StorageFileNotFoundException extends LostBusinessException {

    private static final String MESSAGE = "파일을 찾을 수 없습니다: ";

    public StorageFileNotFoundException(String fileName) {
        super(MESSAGE + fileName);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
