package com.hui.huiaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TravelAssistantVectorStoreConfig {

    @Resource
    private TravelAssistsntDocumentLoader travelAssistsntDocumentLoader;

    @Bean
    VectorStore travelAssistantVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
        List<Document> documents = travelAssistsntDocumentLoader.loadMarkdown();
        vectorStore.add(documents);
        return vectorStore;
    }
}
