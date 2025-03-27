package org.example.ai_langchain4j_demo;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
public class OllamaEmbeddingTest {
    @Value("${langchain4j.embedding-model.ollama.base-url}")
    private String baseUrl;

    @Value("${langchain4j.embedding-model.ollama.model}")
    private String modelName;


    @Test
    public void test() {

        EmbeddingModel embeddedModel = OllamaEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();

        String text = "你是谁";
        Response<Embedding> response = embeddedModel.embed(text);
        log.info("response: {}", response);
        assertThat(response.content().vector()).isNotEmpty();
        assertThat(response.content().dimension()).isEqualTo(embeddedModel.dimension());

        assertThat(response.tokenUsage()).isNull();
        assertThat(response.finishReason()).isNull();
    }
}
