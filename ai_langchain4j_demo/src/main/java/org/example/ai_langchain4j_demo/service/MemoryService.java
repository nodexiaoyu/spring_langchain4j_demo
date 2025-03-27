package org.example.ai_langchain4j_demo.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.example.ai_langchain4j_demo.Mapper.ConsultationLogMapper;
import org.example.ai_langchain4j_demo.dto.ConsultationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryService {
    
    @Autowired
    private ConsultationLogMapper consultationLogMapper;
    
    // 内存中缓存会话，避免频繁数据库访问
    private final Map<String, ChatMemory> sessionMemories = new ConcurrentHashMap<>();
    
    // 获取或创建会话记忆
    public ChatMemory getOrCreateMemory(String sessionId, Integer userId) {
        return sessionMemories.computeIfAbsent(sessionId, k -> {
            // 从数据库加载历史会话
            List<ConsultationLog> logs = consultationLogMapper.findBySessionId(sessionId);
            
            // 创建会话记忆对象，设置窗口大小为10条消息
            ChatMemory memory = MessageWindowChatMemory.builder()
                    .maxMessages(30)
                    .build();
            
            // 将历史记录加载到记忆中
            for (ConsultationLog log : logs) {
                memory.add(dev.langchain4j.data.message.UserMessage.from(log.getQuestionContent()));
                memory.add(dev.langchain4j.data.message.AiMessage.from(log.getAiResponse()));
            }
            
            return memory;
        });
    }
    
    // 保存会话到数据库
    public void saveMessage(ConsultationLog log) {
        consultationLogMapper.insert(log);
    }
    
    // 清除会话缓存
    public void clearMemory(String sessionId) {
        sessionMemories.remove(sessionId);
    }
}
