package education.sapios.Sapios.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

public enum LanguageModelConfig {
    INSTANCE();

    public ChatLanguageModel createChatLanguageModel() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config/ai.properties")) {
            properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load ai.properties file", exception);
        }
        @Nullable String baseUrl = properties.getProperty("baseUrl", null);
        @Nullable String modelName = properties.getProperty("modelName", null);
        @Nullable String apiKey = properties.getProperty("apiKey", null);
        Objects.requireNonNull(baseUrl, "'baseUrl' must be set in config/ai.properties");
        Objects.requireNonNull(modelName, "'modelName' must be set in config/ai.properties");
        Objects.requireNonNull(apiKey, "'apiKey' must be set in config/ai.properties");
        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .maxTokens(8192)
                .timeout(Duration.ofSeconds(60))
                .build();
    }

    public StreamingChatLanguageModel createStreamingChatLanguageModel() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config/ai.properties")) {
            properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load ai.properties file", exception);
        }
        @Nullable String baseUrl = properties.getProperty("baseUrl", null);
        @Nullable String modelName = properties.getProperty("modelName", null);
        @Nullable String apiKey = properties.getProperty("apiKey", null);
        Objects.requireNonNull(baseUrl, "'baseUrl' must be set in config/ai.properties");
        Objects.requireNonNull(modelName, "'modelName' must be set in config/ai.properties");
        Objects.requireNonNull(apiKey, "'apiKey' must be set in config/ai.properties");

        StreamingChatLanguageModel model = GoogleAiGeminiStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();

        return model;
    }
}
