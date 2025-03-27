package org.example.ai_langchain4j_demo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    @TableField("trace_id")
    private String traceId;  // 用于追踪的唯一ID

    @TableField("user_id")
    private Integer userId;  // 操作用户ID，可为空

    @TableField("user_account")
    private String userAccount;  // 操作用户账号，可为空

    @TableField("operation_type")
    private String operationType;  // 操作类型：CREATE, UPDATE, DELETE, QUERY, LOGIN, LOGOUT等

    @TableField("module")
    private String module;  // 操作模块：用户管理、政策管理等

    @TableField("operation_desc")
    private String operationDesc;  // 操作描述

    @TableField("method")
    private String method;  // 请求方法

    @TableField("request_url")
    private String requestUrl;  // 请求URL

    @TableField("request_method")
    private String requestMethod;  // 请求方式：GET, POST等

    @TableField("request_params")
    private String requestParams;  // 请求参数

    @TableField("response_result")
    private String responseResult;  // 响应结果

    @TableField("ip_address")
    private String ipAddress;  // 操作IP地址

    @TableField("status")
    private Integer status;  // 操作状态：0失败，1成功

    @TableField("error_message")
    private String errorMessage;  // 错误信息

    @TableField("operation_time")
    private LocalDateTime operationTime;  // 操作时间

    @TableField("execution_time")
    private Long executionTime;  // 执行时长(ms)
}