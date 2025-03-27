package org.example.ai_langchain4j_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.UnifiedIdentity;
import org.example.ai_langchain4j_demo.service.UnifiedIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "统一身份认证", description = "统一身份认证相关接口")
@RestController
@RequestMapping("/api/identity")
public class UnifiedIdentityController {
    @Autowired
    private UnifiedIdentityService unifiedIdentityService;

    /**
     * 添加统一身份信息
     */
    @Operation(summary = "添加统一身份信息", description = "异步添加一个新的统一身份记录")
    @PostMapping("/add")
    public CompletableFuture<ApiResult<UnifiedIdentity>> addIdentity(
            @Parameter(description = "统一身份对象", required = true) @RequestBody UnifiedIdentity identity) {
        return unifiedIdentityService.addIdentityAsync(identity);
    }

    /**
     * 根据用户账号获取统一身份信息
     */
    @Operation(summary = "根据用户账号获取统一身份信息", description = "通过用户账号查询统一身份记录")
    @GetMapping("/{userAccount}")
    public CompletableFuture<ApiResult<UnifiedIdentity>> getIdentity(
            @Parameter(description = "用户账号", required = true, example = "user123") 
            @PathVariable String userAccount) {
        return unifiedIdentityService.getIdentityAsync(userAccount);
    }

    /**
     * 根据身份类型获取统一身份列表
     */
    @Operation(summary = "根据身份类型获取统一身份列表", description = "通过身份类型查询统一身份记录列表")
    @GetMapping("/type/{type}")
    public CompletableFuture<ApiResult<List<UnifiedIdentity>>> getByType(
            @Parameter(description = "身份类型(学生/教师)", required = true, example = "STUDENT") 
            @PathVariable UnifiedIdentity.IdentityType type) {
        return unifiedIdentityService.getByTypeAsync(type);
    }
}
