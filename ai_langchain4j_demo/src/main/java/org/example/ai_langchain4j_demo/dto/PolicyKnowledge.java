package org.example.ai_langchain4j_demo.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("policy_knowledge")
public class PolicyKnowledge {
    @TableId(value = "knowledge_id", type = IdType.AUTO)
    private Integer knowledgeId;

    @TableField("knowledge_type")
    private KnowledgeType knowledgeType; // 枚举

    @TableField("knowledge_title")
    private String knowledgeTitle;

    private String content;

    @TableField("effective_date")
    private LocalDate effectiveDate;

    @TableField("attachment_url")
    private String attachmentUrl;

    @TableField("status")
    private PolicyStatus status;

    @TableField("update_time")
    private LocalDateTime updateTime;

    public enum KnowledgeType {
        奖学金, 违纪处理, 宿舍管理, 医保报销
    }

    public enum PolicyStatus {
        生效, 过期, 修订中
    }
}
