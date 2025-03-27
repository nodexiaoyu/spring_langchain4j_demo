package org.example.ai_langchain4j_demo.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import org.example.ai_langchain4j_demo.dto.ConsultationLog;
import org.example.ai_langchain4j_demo.tools.PolicyTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

@Service
public class ChatService {
    
    @Value("${langchain4j.siliconflow.api-key}")
    private String apiKey;

    @Value("${langchain4j.siliconflow.base-url}")
    private String baseUrl;

    @Value("${langchain4j.siliconflow.model}")
    private String modelNmae;
    @Autowired
    private MemoryService memoryService;
    
    @Autowired
    private PolicyTool policyTool;
    
    @Autowired
    private ConsultationLogService consultationLogService;
    
    // 系统提示词
    private static final String SYSTEM_PROMPT = 
            "你是一个学校政策咨询助手，负责回答学生关于学校政策的问题。" +
            "你可以使用工具来查询政策信息。请用简洁、准确的方式回答问题。" +
            "如果不确定，请说明你不确定，不要编造信息。";
    
    // 同步聊天接口
    public String chat(String sessionId, Integer userId, String question) {
        // 获取会话记忆
        ChatMemory memory = memoryService.getOrCreateMemory(sessionId, userId);
        
        // 创建聊天模型
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelNmae)
                .temperature(0.7)
                .timeout(Duration.ofSeconds(60))
                .build();
        
        // 创建AI服务 - 修改这部分代码
        PolicyAssistant assistant = AiServices.builder(PolicyAssistant.class)
                .chatLanguageModel(model)
                .chatMemory(memory)
                // 移除 systemMessage 方法调用，改为在构建消息时添加
                .tools(policyTool)
                .build();
        
        // 获取回答
        String answer = assistant.chat(question);
        
        // 保存对话记录
        saveConsultationLog(userId, question, answer, sessionId);
        
        return answer;
    }
    
    // 流式聊天接口 - 使用 StreamingChatLanguageModel
    public void chatStream(String sessionId, Integer userId, String question, SseEmitter emitter) {
        // 获取会话记忆
        ChatMemory memory = memoryService.getOrCreateMemory(sessionId, userId);
        
        // 创建流式聊天模型
        StreamingChatLanguageModel streamingModel = OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelNmae)
                .temperature(0.7)
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();
        
        // 创建流式AI服务 - 移除systemMessage方法调用
        StreamingPolicyAssistant streamingAssistant = AiServices.builder(StreamingPolicyAssistant.class)
                .streamingChatLanguageModel(streamingModel)
                .build();
        
        StringBuilder fullResponse = new StringBuilder();
        CountDownLatch latch = new CountDownLatch(1);
        
        try {
            // 添加用户消息到记忆
            memory.add(UserMessage.from(question));
            
            // 获取流式回答
            TokenStream tokenStream = streamingAssistant.chat(question);
            
            // 处理流式响应
            tokenStream
                    .onPartialResponse(token -> {
                        try {
                            fullResponse.append(token);
                            emitter.send(token);
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    })
                    .onCompleteResponse(completeResponse -> {
                        try {
                            // 获取完整响应文本 - completeResponse 可能是 ChatResponse 类型
                            String responseText = completeResponse.toString();
                            
                            // 添加AI回答到记忆
                            memory.add(AiMessage.from(responseText));
                            
                            // 保存对话记录
                            saveConsultationLog(userId, question, responseText, sessionId);
                            latch.countDown();
                        } catch (Exception e) {
                            emitter.completeWithError(e);
                            latch.countDown();
                        }
                    })
                    .onError(e -> {
                        emitter.completeWithError(e);
                        latch.countDown();
                    })
                    .start();
            
            // 等待流式响应完成
            latch.await();
            emitter.complete();
            
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
    
    // 保存咨询记录
    private void saveConsultationLog(Integer userId, String question, String answer, String sessionId) {
        ConsultationLog log = new ConsultationLog();
        log.setUserId(userId);
        log.setQuestionContent(question);
        log.setAiResponse(answer);
        log.setSessionId(sessionId);
        log.setConsultTime(LocalDateTime.now());
        log.setKnowledgeIds(new ArrayList<>()); // 这里可以添加相关的知识ID
        
        consultationLogService.addLogAsync(log);
    }
    
    // AI助手接口定义
    @dev.langchain4j.service.SystemMessage(SYSTEM_PROMPT)
    interface PolicyAssistant {
        String chat(String userMessage);
    }
    
    // 流式AI助手接口定义 - 添加系统消息注解
    @dev.langchain4j.service.SystemMessage(SYSTEM_PROMPT)
    interface StreamingPolicyAssistant {
        TokenStream chat(String userMessage);
    }
}
