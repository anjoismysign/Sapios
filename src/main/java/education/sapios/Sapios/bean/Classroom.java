package education.sapios.Sapios.bean;

import dev.langchain4j.service.AiServices;
import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.service.AIService;
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
    private String currentMessage;
    private Topic topic;
    private AIService aiService;
    boolean processing = false;

    @PostConstruct
    public void init() {
        Object flashTopic = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash().get("topic");
        if (flashTopic != null) {
            topic = (Topic) flashTopic;
            messages.add(new BubbleChatMessage(topic.getPrompt(), BubbleChatMessage.Sender.BOT));
            aiService = AiServices.builder(AIService.class)
                    .streamingChatLanguageModel(AIService.STREAMING_CHAT_LANGUAGE_MODEL)
                    .systemMessageProvider(id->topic.getSystemPrompt())
                    .build();
        }
    }

    public boolean isProcessing() {
        return processing;
    }


    public void sendMessage() {
        if (isProcessing())
            return;
        currentMessage = currentMessage == null ? null : currentMessage.trim();
        if (currentMessage != null && !currentMessage.isEmpty()) {
            messages.add(new BubbleChatMessage(currentMessage, BubbleChatMessage.Sender.USER));
            processing = true;
            BubbleChatMessage botMessage = new BubbleChatMessage("", BubbleChatMessage.Sender.BOT);
            messages.add(botMessage);
            CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                aiService.generate(currentMessage)
                        .onNext(botMessage::stream)
                        .onComplete(response->{processing = false;})
                        .onError(throwable -> {throwable.printStackTrace(); processing = false;})
                        .start();
            });
            future.exceptionally(throwable -> {
                processing = false;
                throwable.printStackTrace();
                return null;
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
