package education.sapios.Sapios.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.Result;

import java.time.Duration;

public interface AIService {

    ChatLanguageModel CHAT_LANGUAGE_MODEL = OpenAiChatModel.builder()
            .baseUrl("https://api.cerebras.ai/v1")
            .apiKey("csk-xdmcmmv46nm2kpfemf6p8ytdxyd6hmptt3f2f6xtk2kydnfc")
            .modelName("llama3.3-70b")
            .maxTokens(8192)
            .timeout(Duration.ofSeconds(60))
            .build();

    Result<String> generate(String input);
}