package org.example.ai_langchain4j_demo.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**");
    }

    /**
     * TraceId拦截器，为每个请求生成唯一的追踪ID
     */
    public static class TraceIdInterceptor implements HandlerInterceptor {
        private static final String TRACE_ID = "traceId";

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            // 获取请求头中的traceId，如果没有则生成一个新的
            String traceId = request.getHeader(TRACE_ID);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString().replace("-", "");
            }
            
            // 将traceId放入MDC
            MDC.put(TRACE_ID, traceId);
            
            // 添加到响应头，方便前端获取
            response.setHeader(TRACE_ID, traceId);
            
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            // 请求完成后清除MDC中的数据，防止内存泄漏
            MDC.remove(TRACE_ID);
        }
    }
}