package com.minlog.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {
 *      "code" : "400",
 *      "message" : "잘못된 요청입니다.",
 *      "validation" : {
 *          "title" : "값을 입력해주세요"
 *      }
 * }
 */

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.validation = new HashMap<>();
    }

    public void addValidation(String field, String errorMessage) {
        validation.put(field, errorMessage);
    }
}
