package org.example.ai_langchain4j_demo.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private Integer userId;
    private String message;
    private String sessionId;
}