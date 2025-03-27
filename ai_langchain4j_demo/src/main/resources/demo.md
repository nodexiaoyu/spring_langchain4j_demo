好的，我来帮你创建一个详细的 API 接口文档。

```markdown:c:\Users\hbsi\Desktop\langchain4\ai_langchain4j_demo\api-docs.md
# 学生手册智能问答系统 API 文档

## 目录
- [统一响应格式](#统一响应格式)
- [用户管理](#用户管理)
- [统一身份认证](#统一身份认证)
- [政策知识库](#政策知识库)
- [咨询记录](#咨询记录)
- [常见问题](#常见问题)

## 统一响应格式
```json
{
  "code": 200,       // 状态码：200成功，其他表示失败
  "message": "操作成功", // 响应消息
  "data": {}         // 响应数据
}
```

## 用户管理
### 添加用户
- 请求路径：`/api/user/add`
- 请求方法：`POST`
- 请求参数：
```json
{
  "studentNo": "2024001",   // 学号
  "userName": "张三",       // 用户姓名
  "userType": "STUDENT",    // 用户类型：STUDENT/TEACHER
  "email": "zhangsan@example.com",  // 邮箱
  "phone": "13800138000"    // 手机号
}
```

### 更新用户信息
- 请求路径：`/api/user/update`
- 请求方法：`PUT`
- 请求参数：
```json
{
  "userId": 1,              // 用户ID
  "studentNo": "2024001",   // 学号
  "userName": "张三",       // 用户姓名
  "email": "zhangsan@example.com",  // 邮箱
  "phone": "13800138000"    // 手机号
}
```

### 根据ID查询用户
- 请求路径：`/api/user/getById/{userId}`
- 请求方法：`GET`
- 路径参数：
  - userId：用户ID

### 根据学号查询用户
- 请求路径：`/api/user/getByStudentNo/{studentNo}`
- 请求方法：`GET`
- 路径参数：
  - studentNo：学号

## 统一身份认证
### 添加身份信息
- 请求路径：`/api/identity/add`
- 请求方法：`POST`
- 请求参数：
```json
{
  "userAccount": "zhangsan",     // 用户账号
  "identityType": "STUDENT",     // 身份类型：STUDENT/TEACHER
  "realName": "张三",           // 真实姓名
  "identityNo": "2024001",      // 身份证号/工号/学号
  "department": "计算机学院"     // 所属部门
}
```

### 查询身份信息
- 请求路径：`/api/identity/{userAccount}`
- 请求方法：`GET`
- 路径参数：
  - userAccount：用户账号

### 按类型查询身份列表
- 请求路径：`/api/identity/type/{type}`
- 请求方法：`GET`
- 路径参数：
  - type：身份类型（STUDENT/TEACHER）

## 政策知识库
### 按类型查询政策
- 请求路径：`/api/policy/type/{type}`
- 请求方法：`GET`
- 路径参数：
  - type：政策类型（奖学金/违纪处理/宿舍管理/医保报销）

### 查询生效政策
- 请求路径：`/api/policy/active`
- 请求方法：`GET`

### 关键词搜索政策
- 请求路径：`/api/policy/search`
- 请求方法：`GET`
- 查询参数：
  - keyword：搜索关键词

## 咨询记录
### 记录咨询日志
- 请求路径：`/api/consultation/log`
- 请求方法：`POST`
- 请求参数：
```json
{
  "userId": 1,                   // 用户ID
  "questionContent": "问题内容",  // 问题内容
  "aiResponse": "AI回答内容",    // AI回答内容
  "knowledgeIds": [1, 2, 3],    // 关联的知识点ID列表
  "sessionId": "xxx",           // 会话ID
  "satisfaction": 5             // 满意度评分(1-5)
}
```

### 查询会话记录
- 请求路径：`/api/consultation/session/{sessionId}`
- 请求方法：`GET`
- 路径参数：
  - sessionId：会话ID

### 查询用户咨询历史
- 请求路径：`/api/consultation/user/{userId}`
- 请求方法：`GET`
- 路径参数：
  - userId：用户ID

## 常见问题
### 搜索FAQ
- 请求路径：`/api/faq/search`
- 请求方法：`GET`
- 查询参数：
  - keyword：搜索关键词

### 添加FAQ
- 请求路径：`/api/faq/add`
- 请求方法：`POST`
- 请求参数：
```json
{
  "questionPattern": "如何申请奖学金？",  // 问题模式
  "answer": "详细的回答内容",           // 答案内容
  "category": "奖学金",               // 问题类别
  "keywords": ["奖学金", "申请"]       // 关键词列表
}
```

### 删除FAQ
- 请求路径：`/api/faq/{id}`
- 请求方法：`DELETE`
- 路径参数：
  - id：FAQ ID

## 错误码说明
- 200：成功
- 400：请求参数错误
- 401：未授权
- 403：访问被拒绝
- 404：资源不存在
- 500：服务器内部错误

## 注意事项
1. 所有请求都需要在 Header 中携带 token（如果已实现认证）
2. 日期时间格式统一使用 ISO-8601 标准
3. 分页接口统一使用 page（页码，从1开始）和 size（每页条数）参数
```

这个文档提供了所有接口的详细说明，包括：
1. 请求路径
2. 请求方法
3. 请求参数
4. 响应格式
5. 错误码说明
6. 注意事项

你可以根据实际需求调整文档内容，比如添加更多的示例响应或者补充其他接口说明。