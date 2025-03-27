package org.example.ai_langchain4j_demo.service;

import org.example.ai_langchain4j_demo.Mapper.UnifiedIdentityMapper;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.UnifiedIdentity;
import org.example.ai_langchain4j_demo.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UnifiedIdentityService {
    @Autowired
    private UnifiedIdentityMapper unifiedIdentityMapper;
    
    @Autowired
    @Qualifier("virtualThreadExecutor")
    private ThreadPoolTaskExecutor virtualThreadExecutor;

    public CompletableFuture<ApiResult<UnifiedIdentity>> addIdentityAsync(UnifiedIdentity identity) {
        return CompletableFuture.supplyAsync(() -> {
            if (identity == null || StringUtils.isEmpty(identity.getUserAccount())) {
                throw new BizException(400, "用户账号不能为空");
            }
            unifiedIdentityMapper.insert(identity);
            return ApiResult.success(identity);
        }, virtualThreadExecutor);
    }

    public CompletableFuture<ApiResult<UnifiedIdentity>> getIdentityAsync(String userAccount) {
        return CompletableFuture.supplyAsync(() -> {
            UnifiedIdentity identity = unifiedIdentityMapper.selectById(userAccount);
            return ApiResult.success(identity);
        }, virtualThreadExecutor);
    }

    public CompletableFuture<ApiResult<List<UnifiedIdentity>>> getByTypeAsync(UnifiedIdentity.IdentityType type) {
        return CompletableFuture.supplyAsync(() -> {
            List<UnifiedIdentity> identities = unifiedIdentityMapper.findByIdentityType(type);
            return ApiResult.success(identities);
        }, virtualThreadExecutor);
    }
}