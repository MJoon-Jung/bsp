package com.lost.common.domain.exception;

public class ResourceNotFoundException extends LostBusinessException {

    private static final String MESSAGE = "[%s]: %s %s를 찾을 수 없습니다.";

    public ResourceNotFoundException(String datasource, Long id) {
        super(String.format(MESSAGE, datasource, "ID", id));
    }

    public ResourceNotFoundException(String datasource, String propertyName, Long property) {
        super(String.format(MESSAGE, datasource, propertyName, property));
    }

    public ResourceNotFoundException(String datasource, String propertyName, String property) {
        super(String.format(MESSAGE, datasource, propertyName, property));
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
