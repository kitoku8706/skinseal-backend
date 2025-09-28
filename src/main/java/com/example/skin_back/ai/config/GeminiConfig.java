package com.example.skin_back.ai.config;

// import com.google.cloud.vertexai.VertexAI;
// import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Bean
    public Object geminiModel() throws Exception {
//        System.setProperty("gemini.api.key", geminiApiKey);
//        try (VertexAI vertexAi = new VertexAI.Builder().setApiKey(geminiApiKey).build()) {
//            return new GenerativeModel("gemini-2.5-flash", vertexAi);
//        } catch (Exception e) {
//            System.err.println("Gemini Model 초기화 실패: " + e.getMessage());
//            throw e;
//        }
        return null;
    }
}