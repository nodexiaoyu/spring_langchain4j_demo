package org.example.ai_langchain4j_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.ChatRequest;
import org.example.ai_langchain4j_demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "智能聊天", description = "AI聊天接口")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Operation(summary = "发送聊天消息", description = "向AI发送消息并获取响应")
    @PostMapping("/send")
    public ApiResult<String> sendMessage(
            @Parameter(description = "聊天请求，包含用户消息", required = true)
            @RequestBody ChatRequest chatRequest) {
        // 从请求中获取必要参数
        String sessionId = chatRequest.getSessionId() != null ? 
                chatRequest.getSessionId() : UUID.randomUUID().toString();
        Integer userId = chatRequest.getUserId();
        String message = chatRequest.getMessage();
        
        // 调用服务层方法
        String response = chatService.chat(sessionId, userId, message);
        
        return ApiResult.success(response);
    }

    @Operation(summary = "流式聊天", description = "通过SSE实现流式聊天")
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(
            @Parameter(description = "用户消息", required = true, example = "你好")
            @RequestParam String message,
            @Parameter(description = "会话ID", required = false)
            @RequestParam(required = false) String sessionId,
            @Parameter(description = "用户ID", required = false, example = "1001")
            @RequestParam(required = false, defaultValue = "1001") Integer userId) {
        
        // 如果没有提供sessionId，则生成一个新的
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }
        
        // 创建SSE发射器
        SseEmitter emitter = new SseEmitter(180000L); // 3分钟超时
        
        // 调用服务层方法
        chatService.chatStream(sessionId, userId, message, emitter);
        
        return emitter;
    }
}
