package org.example.ai_langchain4j_demo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.ai_langchain4j_demo.annotation.LogOperation;
import org.example.ai_langchain4j_demo.dto.OperationLog;
import org.example.ai_langchain4j_demo.service.OperationLogService;
import org.example.ai_langchain4j_demo.util.IpUtil;
import org.example.ai_langchain4j_demo.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    
    @Autowired
    private OperationLogService operationLogService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Pointcut("@annotation(org.example.ai_langchain4j_demo.annotation.LogOperation)")
    public void logPointCut() {}
    
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        
        // 生成追踪ID并放入MDC
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put("traceId", traceId);
        
        // 执行方法
        Object result = null;
        Exception ex = null;
        try {
            result = point.proceed();
            return result;
        } catch (Exception e) {
            ex = e;
            throw e;
        } finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            // 保存日志
            saveLog(point, result, time, ex, traceId);
            // 清除MDC
            MDC.remove("traceId");
        }
    }
    
    private void saveLog(ProceedingJoinPoint joinPoint, Object result, long time, Exception ex, String traceId) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LogOperation logAnnotation = method.getAnnotation(LogOperation.class);
            
            // 获取request
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
            
            // 构建操作日志
            OperationLog operationLog = new OperationLog();
            operationLog.setTraceId(traceId);
            
            // 设置用户信息
            Integer userId = SecurityUtil.getCurrentUserId();
            String userAccount = SecurityUtil.getCurrentUserAccount();
            operationLog.setUserId(userId);
            operationLog.setUserAccount(userAccount);
            
            // 设置操作信息
            operationLog.setOperationType(logAnnotation.operationType());
            operationLog.setModule(logAnnotation.module());
            operationLog.setOperationDesc(logAnnotation.description());
            operationLog.setMethod(method.getDeclaringClass().getName() + "." + method.getName());
            
            // 设置请求信息
            if (request != null) {
                operationLog.setRequestUrl(request.getRequestURI());
                operationLog.setRequestMethod(request.getMethod());
                operationLog.setIpAddress(IpUtil.getIpAddr(request));
                
                // 保存请求参数
                if (logAnnotation.saveRequestData()) {
                    String params = objectMapper.writeValueAsString(joinPoint.getArgs());
                    operationLog.setRequestParams(params);
                }
            }
            
            // 保存响应结果
            if (logAnnotation.saveResponseData() && result != null) {
                operationLog.setResponseResult(objectMapper.writeValueAsString(result));
            }
            
            // 设置状态和错误信息
            if (ex != null) {
                operationLog.setStatus(0);
                operationLog.setErrorMessage(ex.getMessage());
            } else {
                operationLog.setStatus(1);
            }
            
            operationLog.setOperationTime(LocalDateTime.now());
            operationLog.setExecutionTime(time);
            
            // 保存日志
            operationLogService.saveLog(operationLog);
            
            // 同时输出到控制台日志
            log.info("操作日志: traceId={}, 用户={}, 模块={}, 操作={}, 耗时={}ms, 状态={}", 
                    traceId, userAccount, operationLog.getModule(), 
                    operationLog.getOperationDesc(), time, 
                    operationLog.getStatus() == 1 ? "成功" : "失败");
            
        } catch (Exception e) {
            log.error("记录操作日志异常", e);
        }
    }
}