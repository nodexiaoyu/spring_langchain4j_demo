package org.example.ai_langchain4j_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.Faq;
import org.example.ai_langchain4j_demo.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "常见问题", description = "FAQ管理接口")
@RestController
@RequestMapping("/api/faq")
public class FaqController {
    @Autowired
    private FaqService faqService;

    @Operation(summary = "搜索FAQ", description = "关键词搜索常见问题")
    @GetMapping("/search")
    public CompletableFuture<ApiResult<List<Faq>>> search(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return faqService.searchAsync(keyword);
    }

    @Operation(summary = "添加FAQ", description = "添加新的常见问题")
    @PostMapping("/add")
    public CompletableFuture<ApiResult<Faq>> addFaq(
            @Parameter(description = "FAQ信息，包含问题和答案") @RequestBody Faq faq) {
        return faqService.addFaqAsync(faq);
    }

    @Operation(summary = "删除FAQ", description = "删除指定的常见问题")
    @DeleteMapping("/{id}")
    public CompletableFuture<ApiResult<Void>> deleteFaq(
            @Parameter(description = "FAQ ID") @PathVariable Integer id) {
        return faqService.deleteFaqAsync(id);
    }
}