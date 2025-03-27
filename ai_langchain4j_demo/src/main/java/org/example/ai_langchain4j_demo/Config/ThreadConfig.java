package org.example.ai_langchain4j_demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executors;

@Configuration
public class ThreadConfig {
    
    @Bean
    public ThreadPoolTaskExecutor virtualThreadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(100);
        executor.setThreadFactory(Executors.defaultThreadFactory());
        executor.setThreadNamePrefix("virtual-thread-");
        executor.initialize();
        return executor;
    }
}