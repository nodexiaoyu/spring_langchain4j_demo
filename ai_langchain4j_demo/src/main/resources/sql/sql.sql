

-- 按照依赖顺序创建表
-- 1. 统一身份表（基础表）
CREATE TABLE unified_identity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_account VARCHAR(50) NOT NULL COMMENT '用户账号',
    identity_type VARCHAR(20) NOT NULL COMMENT '身份类型',
    real_name VARCHAR(50) COMMENT '真实姓名',
    identity_no VARCHAR(50) COMMENT '身份证号/工号/学号',
    department VARCHAR(100) COMMENT '所属部门',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_user_account` (`user_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一身份认证表';

-- 2. 用户表（依赖统一身份表）
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    student_no CHAR(12) UNIQUE NOT NULL COMMENT '学号',
    real_name VARCHAR(50),
    department VARCHAR(100) COMMENT '所属院系',
    grade ENUM('2022','2023','2024','2025'),
    last_login DATETIME,
    CONSTRAINT fk_student_no FOREIGN KEY (student_no) 
        REFERENCES unified_identity(user_account)
) COMMENT '对接校园身份平台[1](@ref)';

-- 3. 政策知识表
CREATE TABLE policy_knowledge (
    knowledge_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '知识条目ID',
    knowledge_type ENUM('奖学金','违纪处理','宿舍管理','医保报销') NOT NULL COMMENT '对应《学生手册》32类政策',
    knowledge_title VARCHAR(200) NOT NULL COMMENT '如「国家奖学金申请条件」',
    content TEXT NOT NULL COMMENT '政策条款全文',
    effective_date DATE COMMENT '政策生效日期',
    attachment_url VARCHAR(500) COMMENT '关联文件路径',
    status ENUM('生效','过期','修订中') DEFAULT '生效',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '存储《学生手册》结构化政策数据[1](@ref)';

-- 4. 咨询记录表（依赖用户表）
CREATE TABLE consultation_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '关联统一身份认证ID[1](@ref)',
    question_content TEXT NOT NULL COMMENT '原始提问文本',
    ai_response TEXT COMMENT 'AI生成回答',
    knowledge_ids JSON COMMENT '关联调用的政策ID数组',
    session_id VARCHAR(64) COMMENT '连续对话会话ID',
    consult_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    satisfaction TINYINT COMMENT '满意度评分（1-5星）',
    INDEX idx_user_time (user_id, consult_time),
    CONSTRAINT fk_consultation_user FOREIGN KEY (user_id) 
        REFERENCES user(user_id)
) COMMENT '记录7×24小时咨询数据[1](@ref)';

-- 5. FAQ表
CREATE TABLE faq (
    faq_id INT AUTO_INCREMENT PRIMARY KEY,
    question_pattern VARCHAR(500) NOT NULL COMMENT '标准化问题表述',
    standard_answer TEXT NOT NULL,
    related_knowledge_ids JSON COMMENT '关联政策知识ID',
    call_count INT DEFAULT 0 COMMENT '历史调用次数',
    last_updated DATE COMMENT '最后更新日期',
    FULLTEXT INDEX idx_question (question_pattern)
) COMMENT '基于万份调研的案例库[1](@ref)';

-- 6. 工单表（依赖用户表）
CREATE TABLE support_ticket (
    ticket_id CHAR(16) PRIMARY KEY COMMENT '工单号（如T202503220001）',
    user_id INT NOT NULL,
    problem_type ENUM('政策申诉','系统异常','人工咨询') NOT NULL,
    description TEXT,
    processor_id INT COMMENT '处理人ID',
    status ENUM('待受理','处理中','已解决','已关闭'),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    complete_time DATETIME,
    INDEX idx_status_time (status, create_time),
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) 
        REFERENCES user(user_id),
    CONSTRAINT fk_ticket_processor FOREIGN KEY (processor_id) 
        REFERENCES user(user_id)
) COMMENT '复杂问题转人工流程[2](@ref)';

-- 7. 关键词映射表
CREATE TABLE keyword_mapping (
    keyword VARCHAR(50) PRIMARY KEY COMMENT '如「奖学金」',
    knowledge_ids JSON NOT NULL COMMENT '关联政策ID数组',
    related_keywords JSON COMMENT '近义词（如「助学金」「补助金」）',
    search_count INT DEFAULT 0 COMMENT '语义分析调用次数'
) COMMENT '支撑智能语义分析[1](@ref)';


-- 8. 操作日志表
CREATE TABLE operation_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    trace_id VARCHAR(64) NOT NULL COMMENT '追踪ID',
    user_id INT COMMENT '用户ID',
    user_account VARCHAR(50) COMMENT '用户账号',
    operation_type VARCHAR(50) NOT NULL COMMENT '操作类型',
    module VARCHAR(50) COMMENT '操作模块',
    operation_desc VARCHAR(500) COMMENT '操作描述',
    method VARCHAR(200) COMMENT '请求方法',
    request_url VARCHAR(500) COMMENT '请求URL',
    request_method VARCHAR(10) COMMENT '请求方式',
    request_params TEXT COMMENT '请求参数',
    response_result TEXT COMMENT '响应结果',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    status TINYINT DEFAULT 1 COMMENT '操作状态：0失败，1成功',
    error_message TEXT COMMENT '错误信息',
    operation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    execution_time BIGINT COMMENT '执行时长(ms)',
    INDEX idx_trace_id (trace_id),
    INDEX idx_user_id (user_id),
    INDEX idx_operation_time (operation_time)
) COMMENT '系统操作日志表';