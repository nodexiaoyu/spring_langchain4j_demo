package org.example.ai_langchain4j_demo.service;

import org.example.ai_langchain4j_demo.Mapper.PolicyMapper;
import org.example.ai_langchain4j_demo.dto.PolicyKnowledge;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PolicyService {
    @Autowired
    private PolicyMapper policyMapper;
    
    @Autowired
    private AsyncTaskExecutor virtualThreadExecutor;

    public CompletableFuture<ApiResult<List<PolicyKnowledge>>> findByTypeAsync(PolicyKnowledge.KnowledgeType type) {
        return CompletableFuture.supplyAsync(() -> {
            List<PolicyKnowledge> policies = policyMapper.findByType(type);
            return ApiResult.success(policies);
        }, virtualThreadExecutor);
    }
    
    public CompletableFuture<ApiResult<List<PolicyKnowledge>>> findActivePolicyAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<PolicyKnowledge> policies = policyMapper.findActivePolicy();
            return ApiResult.success(policies);
        }, virtualThreadExecutor);
    }
    
    public CompletableFuture<ApiResult<List<PolicyKnowledge>>> searchByKeywordAsync(String keyword) {
        return CompletableFuture.supplyAsync(() -> {
            List<PolicyKnowledge> policies = policyMapper.searchByKeyword(keyword);
            return ApiResult.success(policies);
        }, virtualThreadExecutor);
    }
}
