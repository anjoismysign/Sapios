package education.sapios.Sapios.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
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
            // failed to load config, defaults will be used
        }
        String baseUrl = properties.getProperty("baseUrl", "https://api.cerebras.ai/v1");
        String modelName = properties.getProperty("modelName", "llama3.3-70b");
        @Nullable String apiKey = properties.getProperty("apiKey", null);
        Objects.requireNonNull(apiKey, "API key must be set in config/ai.properties");
        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .maxTokens(8192)
                .timeout(Duration.ofSeconds(60))
                .build();
    }
}
