package com.minlog.api.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends BlogException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String message;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public InvalidRequest(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int statusCode() {
        return 400;
    }


}
