package education.sapios.Sapios.dto;

import java.util.List;

public class CourseDTO {
    private String name;
    private List<TopicDTO> topics;
    
    // Default constructor
    public CourseDTO() {
    }
    
    // Constructor with parameters
    public CourseDTO(String name, List<TopicDTO> topics) {
        this.name = name;
        this.topics = topics;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<TopicDTO> getTopics() {
        return topics;
    }
    
    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }
}
