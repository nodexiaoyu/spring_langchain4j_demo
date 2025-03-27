package org.example.ai_langchain4j_demo.exception;

import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 处理自定义业务异常
    @ExceptionHandler(BizException.class)
    public ApiResult<?> handleBizException(BizException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleValidationException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("参数校验失败");
        log.warn("参数校验异常: {}", errorMsg);
        return ApiResult.error(400, errorMsg);
    }

    // 处理类型转换异常
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class})
    public ApiResult<?> handleTypeMismatch(Exception e) {
        log.warn("参数类型错误: {}", e.getMessage());
        return ApiResult.error(400, "参数类型错误: " + e.getMessage());
    }
    
    // 处理缺少请求参数异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult<?> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("缺少必要参数: {}", e.getMessage());
        return ApiResult.error(400, "缺少必要参数: " + e.getParameterName());
    }

    // 处理所有未捕获异常
    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleGlobalException(Exception e) {
        log.error("系统异常", e);
        return ApiResult.error(500, "系统繁忙，请稍后再试");
    }
}