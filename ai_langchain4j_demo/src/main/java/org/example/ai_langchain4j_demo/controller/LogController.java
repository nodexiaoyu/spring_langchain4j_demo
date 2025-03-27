package org.example.ai_langchain4j_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ai_langchain4j_demo.annotation.LogOperation;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.OperationLog;
import org.example.ai_langchain4j_demo.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "系统日志", description = "系统操作日志查询接口")
@RestController
@RequestMapping("/api/logs")
public class LogController {
    
    @Autowired
    private OperationLogService operationLogService;
    
    @Operation(summary = "根据追踪ID查询日志", description = "通过追踪ID查询相关联的操作日志")
    @GetMapping("/trace/{traceId}")
    @LogOperation(module = "日志管理", operationType = "查询", description = "根据追踪ID查询日志")
    public CompletableFuture<ApiResult<List<OperationLog>>> getLogsByTraceId(
            @Parameter(description = "追踪ID") @PathVariable String traceId) {
        return operationLogService.findByTraceIdAsync(traceId);
    }
    
    @Operation(summary = "查询用户最近操作日志", description = "查询指定用户的最近操作日志")
    @GetMapping("/user/{userId}")
    @LogOperation(module = "日志管理", operationType = "查询", description = "查询用户最近操作日志")
    public CompletableFuture<ApiResult<List<OperationLog>>> getUserRecentLogs(
            @Parameter(description = "用户ID") @PathVariable Integer userId,
            @Parameter(description = "查询数量限制") @RequestParam(defaultValue = "20") Integer limit) {
        return operationLogService.findRecentByUserIdAsync(userId, limit);
    }
    
    @Operation(summary = "查询模块最近操作日志", description = "查询指定模块的最近操作日志")
    @GetMapping("/module/{module}")
    @LogOperation(module = "日志管理", operationType = "查询", description = "查询模块最近操作日志")
    public CompletableFuture<ApiResult<List<OperationLog>>> getModuleRecentLogs(
            @Parameter(description = "模块名称") @PathVariable String module,
            @Parameter(description = "查询数量限制") @RequestParam(defaultValue = "20") Integer limit) {
        return operationLogService.findRecentByModuleAsync(module, limit);
    }
}