package com.minlog.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
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
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY) //비어있는 json 데이터는 출력 x
public class ErrorResponse {

    private final String code;
    private final String message;
    private Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message,  Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(String field, String errorMessage) {
        validation.put(field, errorMessage);
    }
}
