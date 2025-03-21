package education.sapios.Sapios.bean;

import java.io.Serializable;

public class BubbleChatMessage implements Serializable {

    public enum Sender {
        USER("user"),
        BOT("bubble-bot");

        private final String value;
        Sender(String value) {
            this.value = value;
        }
    }

    private String content;
    private String sender;

    public BubbleChatMessage(String content, Sender sender) {
        this.content = content;
        this.sender = sender.value;
    }

    public BubbleChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
