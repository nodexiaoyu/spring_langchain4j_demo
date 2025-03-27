package org.example.ai_langchain4j_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.ConsultationLog;
import org.example.ai_langchain4j_demo.service.ConsultationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "咨询记录", description = "用户咨询记录管理接口")
@RestController
@RequestMapping("/api/consultation")
public class ConsultationLogController {
    @Autowired
    private ConsultationLogService consultationLogService;

    @Operation(summary = "记录咨询日志", description = "记录用户咨询问题和AI回答")
    @PostMapping("/log")
    public CompletableFuture<ApiResult<ConsultationLog>> addLog(
            @Parameter(description = "咨询记录信息，包含问题和回答")
            @RequestBody ConsultationLog log) {
        return consultationLogService.addLogAsync(log);
    }

    @Operation(summary = "查询会话记录", description = "根据会话ID查询对话历史")
    @GetMapping("/session/{sessionId}")
    public CompletableFuture<ApiResult<List<ConsultationLog>>> getSessionLogs(
            @Parameter(description = "会话ID") @PathVariable String sessionId) {
        return consultationLogService.getSessionLogsAsync(sessionId);
    }

    @Operation(summary = "查询用户咨询历史", description = "查询指定用户的所有咨询记录")
    @GetMapping("/user/{userId}")
    public CompletableFuture<ApiResult<List<ConsultationLog>>> getUserLogs(
            @Parameter(description = "用户ID") @PathVariable Integer userId) {
        return consultationLogService.getUserLogsAsync(userId);
    }
}