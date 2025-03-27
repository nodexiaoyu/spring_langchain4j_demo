package org.example.ai_langchain4j_demo.exception;

public class BizException extends RuntimeException {
    private final int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        this(500, message);
    }

    public int getCode() {
        return code;
    }
}