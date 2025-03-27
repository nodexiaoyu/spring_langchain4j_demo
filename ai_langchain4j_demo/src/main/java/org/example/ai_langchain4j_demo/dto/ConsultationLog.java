package org.example.ai_langchain4j_demo.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("consultation_log")
public class ConsultationLog {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    @TableField("user_id")
    private Integer userId;

    @TableField("question_content")
    private String questionContent;

    @TableField("ai_response")
    private String aiResponse;

    @TableField(value = "knowledge_ids", typeHandler = FastjsonTypeHandler.class)
    private List<Integer> knowledgeIds; // JSON数组

    @TableField("session_id")
    private String sessionId;

    @TableField("consult_time")
    private LocalDateTime consultTime;

    private Integer satisfaction;
}
