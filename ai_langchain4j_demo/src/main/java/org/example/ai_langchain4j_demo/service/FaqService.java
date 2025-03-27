package org.example.ai_langchain4j_demo.service;

import org.example.ai_langchain4j_demo.Mapper.FaqMapper;
import org.example.ai_langchain4j_demo.dto.ApiResult;
import org.example.ai_langchain4j_demo.dto.Faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FaqService {
    @Autowired
    private FaqMapper faqMapper;
    
    @Autowired
    @Qualifier("virtualThreadExecutor")
    private ThreadPoolTaskExecutor virtualThreadExecutor;

    public CompletableFuture<ApiResult<List<Faq>>> searchAsync(String keyword) {
        return CompletableFuture.supplyAsync(() -> {
            List<Faq> faqs = faqMapper.searchByKeyword(keyword);
            return ApiResult.success(faqs);
        }, virtualThreadExecutor);
    }

    public CompletableFuture<ApiResult<Faq>> addFaqAsync(Faq faq) {
        return CompletableFuture.supplyAsync(() -> {
            faqMapper.insert(faq);
            return ApiResult.success(faq);
        }, virtualThreadExecutor);
    }

    public CompletableFuture<ApiResult<Void>> deleteFaqAsync(Integer faqId) {
        return CompletableFuture.supplyAsync(() -> {
            faqMapper.deleteById(faqId);
            return ApiResult.success(null);
        }, virtualThreadExecutor);
    }
}