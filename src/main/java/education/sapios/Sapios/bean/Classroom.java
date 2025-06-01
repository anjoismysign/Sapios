package education.sapios.Sapios.bean;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
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
import java.util.regex.Pattern;

@Named
@ViewScoped
public class Classroom implements Serializable {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();
    private final List<BubbleChatMessage> messages = new ArrayList<>();
    private final List<ChatMessage> chatMessages = new ArrayList<>();
    private String currentMessage;
    private Topic topic;
    private ChatLanguageModel model;
    private CompletableFuture<String> future;
    boolean processing = false;

    private static String regex = "(?s)<think>.*?</think>";

    // Compila la expresión regular
    private static Pattern pattern = Pattern.compile(regex);

    @PostConstruct
    public void init() {
        Object flashTopic = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash().get("topic");
        if (flashTopic != null) {
            topic = (Topic) flashTopic;
            String prompt = topic.getPrompt();
            messages.add(new BubbleChatMessage(prompt, BubbleChatMessage.Sender.BOT));
            chatMessages.add(new AiMessage(prompt));
            model = LanguageModelConfig.INSTANCE.createChatLanguageModel();
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
            future = CompletableFuture.supplyAsync(() -> model.chat(chatMessages).aiMessage().text());
            future.thenAccept(content -> {
                BubbleChatMessage botMessage = new BubbleChatMessage(pattern.matcher(content).replaceAll(""), BubbleChatMessage.Sender.BOT);
                messages.add(botMessage);
                chatMessages.add(new AiMessage(content));
                processing = false;
                // Ya no es necesario limpiar currentMessage aquí
            });
        }
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
