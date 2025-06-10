package education.sapios.Sapios.bean;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.config.LanguageModelConfig;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Named
@ViewScoped
public class Classroom implements Serializable {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();
    private final List<BubbleChatMessage> messages = new ArrayList<>();
    private final List<ChatMessage> chatMessages = new ArrayList<>();
    private String currentMessage;
    private Topic topic;
    private StreamingChatLanguageModel model;
    private CompletableFuture<String> future;
    boolean processing = false;

    @PostConstruct
    public void init() {
        Object flashTopic = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash().get("topic");
        if (flashTopic != null) {
            topic = (Topic) flashTopic;
            String prompt = topic.getPrompt();
            messages.add(new BubbleChatMessage(prompt, BubbleChatMessage.Sender.BOT));
            chatMessages.add(new AiMessage(prompt));
            model = LanguageModelConfig.INSTANCE.createStreamingChatLanguageModel();
        }
    }

    public boolean isProcessing() {
        return processing;
    }

    public void sendMessage() {
        if (future != null && !future.isDone()) {
            return;
        }

        // Guarda el mensaje actual en una variable local
        final String userMessageText = (currentMessage == null) ? null : currentMessage.trim();

        if (userMessageText != null && !userMessageText.isEmpty()) {
            messages.add(new BubbleChatMessage(userMessageText, BubbleChatMessage.Sender.USER));
            chatMessages.add(new UserMessage(userMessageText));

            currentMessage = "";
            processing = true;

            BubbleChatMessage botBubbleChatMessage = new BubbleChatMessage("", BubbleChatMessage.Sender.BOT);
            messages.add(botBubbleChatMessage);
            chatAsync(botBubbleChatMessage);
        }
    }

    public void chatAsync(BubbleChatMessage botBubbleChatMessage){
        model.chat(chatMessages, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String partialResponse) {
                botBubbleChatMessage.append(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                String content = chatResponse.aiMessage().text();
                chatMessages.add(new AiMessage(content));
                processing = false;
            }

            @Override
            public void onError(Throwable throwable) {
                botBubbleChatMessage.setContent("");
                chatAsync(botBubbleChatMessage);
            }
        });

    }

    public String parseMarkdown(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    public List<BubbleChatMessage> getMessages() {
        return messages;
    }

    public List<BubbleChatMessage> getChat(){
        return messages.subList(1, messages.size() - 1);
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage.trim();
    }

    public String getTitle() {
        return topic == null ? "" : topic.display();
    }
}
