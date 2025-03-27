package org.example.ai_langchain4j_demo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("unified_identity")
public class UnifiedIdentity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    @NotNull(message = "用户账号不能为空")
    @TableField("user_account")
    private String userAccount;
    
    @NotNull(message = "身份类型不能为空")
    @TableField("identity_type")
    private IdentityType identityType;

    @TableField("real_name")
    private String realName;

    @TableField("identity_no")
    private String identityNo;

    @TableField("department")
    private String department;

    // 添加身份类型枚举
    public enum IdentityType {
        STUDENT,    // 学生
        TEACHER     // 教师
    }
}
