package org.example.ai_langchain4j_demo.dto;

import lombok.Data;

@Data
public class ApiResult<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static ApiResult<?> error(int code, String message) {
        ApiResult<?> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
