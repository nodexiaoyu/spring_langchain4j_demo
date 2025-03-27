package org.example.ai_langchain4j_demo.Config;

import lombok.extern.slf4j.Slf4j;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Slf4j  // 添加日志支持
public class ErrorConfig implements ErrorController {

    @RequestMapping("/error")
    public ApiResult<?> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        log.error("Error occurred: status={}, path={}", status, path);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            return ApiResult.error(statusCode, 
                switch (statusCode) {
                    case 404 -> "资源未找到";
                    case 403 -> "访问被拒绝";
                    case 401 -> "身份验证失败";
                    default -> HttpStatus.valueOf(statusCode).getReasonPhrase();
                });
        }
        return ApiResult.error(500, "未知错误");
    }
}