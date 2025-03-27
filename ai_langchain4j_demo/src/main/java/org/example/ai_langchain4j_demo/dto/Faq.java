package org.example.ai_langchain4j_demo.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@TableName("faq")
public class Faq {
    @TableId(value = "faq_id", type = IdType.AUTO)
    private Integer faqId;

    @TableField("question_pattern")
    private String questionPattern;

    @TableField("standard_answer")
    private String standardAnswer;

    @TableField(value = "related_knowledge_ids", typeHandler = JacksonTypeHandler.class)
    private List<Integer> relatedKnowledgeIds;

    @TableField("call_count")
    private Integer callCount;

    @TableField("last_updated")
    private LocalDate lastUpdated;
}