package org.example.ai_langchain4j_demo.service;

import org.example.ai_langchain4j_demo.Mapper.OperationLogMapper;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OperationLogService {
    @Autowired
    private OperationLogMapper operationLogMapper;
    
    @Autowired
    @Qualifier("virtualThreadExecutor")
    private ThreadPoolTaskExecutor virtualThreadExecutor;
    
    public void saveLog(OperationLog log) {
        // 使用异步方式保存日志，不影响主业务流程
        CompletableFuture.runAsync(() -> {
            operationLogMapper.insert(log);
        }, virtualThreadExecutor);
    }
    
    public CompletableFuture<ApiResult<List<OperationLog>>> findByTraceIdAsync(String traceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<OperationLog> logs = operationLogMapper.findByTraceId(traceId);
            return ApiResult.success(logs);
        }, virtualThreadExecutor);
    }
    
    public CompletableFuture<ApiResult<List<OperationLog>>> findRecentByUserIdAsync(Integer userId, Integer limit) {
        return CompletableFuture.supplyAsync(() -> {
            List<OperationLog> logs = operationLogMapper.findRecentByUserId(userId, limit);
            return ApiResult.success(logs);
        }, virtualThreadExecutor);
    }
    
    public CompletableFuture<ApiResult<List<OperationLog>>> findRecentByModuleAsync(String module, Integer limit) {
        return CompletableFuture.supplyAsync(() -> {
            List<OperationLog> logs = operationLogMapper.findRecentByModule(module, limit);
            return ApiResult.success(logs);
        }, virtualThreadExecutor);
    }
}