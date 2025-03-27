package org.example.ai_langchain4j_demo.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学生手册智能问答系统 API")
                        .description("基于 LangChain4j 的智能问答系统接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("AI助手")
                                .email("support@example.com")));
    }
}