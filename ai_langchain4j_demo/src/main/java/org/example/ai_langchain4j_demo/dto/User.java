package org.example.ai_langchain4j_demo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField("student_no")
    private String studentNo; // CHAR(12)

    @TableField("real_name")
    private String realName;

    private String department;

    private String grade;

    @TableField("last_login")
    private LocalDateTime lastLogin;
}