package education.sapios.Sapios.dto;

public class TopicDTO {
    private String name;
    private String prompt;
    private int orderNumber;
    
    // Default constructor
    public TopicDTO() {
    }
    
    // Constructor with parameters
    public TopicDTO(String name, String prompt, int orderNumber) {
        this.name = name;
        this.prompt = prompt;
        this.orderNumber = orderNumber;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPrompt() {
        return prompt;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
