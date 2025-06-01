package education.sapios.Sapios.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.Result;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public interface AIService {

    static ChatLanguageModel createChatLanguageModel() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config/ai.properties")) {
            properties.load(input);
        } catch (IOException exception) {
            // failed to load config, defaults will be used
        }
        String baseUrl = properties.getProperty("baseUrl", "https://api.cerebras.ai/v1");
        String modelName = properties.getProperty("modelName", "llama3.3-70b");
        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(System.getenv("SAPIOS_API_KEY"))
                .modelName(modelName)
                .maxTokens(8192)
                .timeout(Duration.ofSeconds(60))
                .build();
    }

    ChatLanguageModel CHAT_LANGUAGE_MODEL = createChatLanguageModel();

    Result<String> generate(String input);
}