package org.example.ai_langchain4j_demo.service;

import org.example.ai_langchain4j_demo.Mapper.ConsultationLogMapper;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.ConsultationLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ConsultationLogService {
    @Autowired
    private ConsultationLogMapper consultationLogMapper;
    
    @Autowired
    @Qualifier("virtualThreadExecutor")
    private ThreadPoolTaskExecutor virtualThreadExecutor;

    public CompletableFuture<ApiResult<ConsultationLog>> addLogAsync(ConsultationLog log) {
        return CompletableFuture.supplyAsync(() -> {
            consultationLogMapper.insert(log);
            return ApiResult.success(log);
        }, virtualThreadExecutor);
    }

    public CompletableFuture<ApiResult<List<ConsultationLog>>> getSessionLogsAsync(String sessionId) {
        return CompletableFuture.supplyAsync(() -> {
            List<ConsultationLog> logs = consultationLogMapper.findBySessionId(sessionId);
            return ApiResult.success(logs);
        }, virtualThreadExecutor);
    }

    public CompletableFuture<ApiResult<List<ConsultationLog>>> getUserLogsAsync(Integer userId) {
        return CompletableFuture.supplyAsync(() -> {
            List<ConsultationLog> logs = consultationLogMapper.findByUserId(userId);
            return ApiResult.success(logs);
        }, virtualThreadExecutor);
    }
}