package org.example.ai_langchain4j_demo.dto;

import lombok.Data;

@Data
public class PolicyDTO {
    
    private Long id;
    
    private String knowledgeTitle;
    
    private String content;
    
    private String type;
}