package education.sapios.Sapios.service;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.TokenStream;

import java.time.Duration;

public interface AIService {

    StreamingChatLanguageModel STREAMING_CHAT_LANGUAGE_MODEL = OpenAiStreamingChatModel.builder()
            .baseUrl("http://localhost:11434/v1")
            .apiKey("ollama")
            .modelName("gemma3:4b")
            .maxTokens(2048)
            .timeout(Duration.ofSeconds(60))
            .build();

    TokenStream generate(String input);
}