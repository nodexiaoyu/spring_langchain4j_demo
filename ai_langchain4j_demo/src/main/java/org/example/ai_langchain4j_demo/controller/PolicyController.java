package org.example.ai_langchain4j_demo.controller;

import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.PolicyKnowledge;
import org.example.ai_langchain4j_demo.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {
    
    @Autowired
    private PolicyService policyService;
    
    @GetMapping("/byType/{type}")
    public CompletableFuture<ApiResult<List<PolicyKnowledge>>> getPoliciesByType(
            @PathVariable PolicyKnowledge.KnowledgeType type) {
        return policyService.findByTypeAsync(type);
    }
    
    @GetMapping("/active")
    public CompletableFuture<ApiResult<List<PolicyKnowledge>>> getActivePolicies() {
        return policyService.findActivePolicyAsync();
    }
    
    @GetMapping("/search")
    public CompletableFuture<ApiResult<List<PolicyKnowledge>>> searchPolicies(
            @RequestParam String keyword) {
        return policyService.searchByKeywordAsync(keyword);
    }
}