package org.example.ai_langchain4j_demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("policy")
public class Policy {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String knowledgeTitle;
    
    private String content;
    
    private String type;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}