package com.lost.common.domain.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class LostBusinessException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public LostBusinessException(String message) {
        super(message);
    }

    public void addValidation(String field, String message) {
        validation.put(field, message);
    }

    public Map<String, String> getValidation() {
        return Collections.unmodifiableMap(validation);
    }

    public abstract int statusCode();
}
