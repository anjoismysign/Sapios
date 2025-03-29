package education.sapios.Sapios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import education.sapios.Sapios.dto.CourseDTO;
import education.sapios.Sapios.dto.TopicDTO;
import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.repository.CourseRepository;
import education.sapios.Sapios.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseImportExportService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private TopicRepository topicRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Export a course to JSON
     * 
     * @param courseId The ID of the course to export
     * @return JSON string representation of the course and its topics
     * @throws IOException If there's an error during JSON serialization
     */
    public String exportCourse(Long courseId) throws IOException {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            throw new IllegalArgumentException("Course not found with ID: " + courseId);
        }
        
        Course course = courseOpt.get();
        List<Topic> topics = topicRepository.findByCourse(course);
        
        // Convert to DTO
        CourseDTO courseDTO = convertToDTO(course, topics);
        
        // Serialize to JSON
        return objectMapper.writeValueAsString(courseDTO);
    }
    
    /**
     * Import a course from JSON
     * 
     * @param json JSON string representation of the course and its topics
     * @return The imported course entity
     * @throws IOException If there's an error during JSON deserialization
     */
    @Transactional
    public Course importCourse(String json) throws IOException {
        // Deserialize from JSON
        CourseDTO courseDTO = objectMapper.readValue(json, CourseDTO.class);
        
        // Check if course already exists
        Optional<Course> existingCourseOpt = courseRepository.findFirstByName(courseDTO.getName());
        Course course;
        
        if (existingCourseOpt.isPresent()) {
            // Update existing course
            course = existingCourseOpt.get();
        } else {
            // Create new course
            course = new Course();
            course.setName(courseDTO.getName());
            course = courseRepository.save(course);
        }
        
        // Delete existing topics if updating
        if (existingCourseOpt.isPresent()) {
            List<Topic> existingTopics = topicRepository.findByCourse(course);
            topicRepository.deleteAll(existingTopics);
        }
        
        // Create topics
        if (courseDTO.getTopics() != null) {
            for (TopicDTO topicDTO : courseDTO.getTopics()) {
                Topic topic = new Topic();
                topic.setName(topicDTO.getName());
                topic.setPrompt(topicDTO.getPrompt());
                topic.setOrderNumber(topicDTO.getOrderNumber());
                topic.setCourse(course);
                topicRepository.save(topic);
            }
        }
        
        return course;
    }
    
    /**
     * Convert a course entity and its topics to a DTO
     * 
     * @param course The course entity
     * @param topics The list of topic entities
     * @return CourseDTO representation
     */
    private CourseDTO convertToDTO(Course course, List<Topic> topics) {
        List<TopicDTO> topicDTOs = topics.stream()
                .map(topic -> new TopicDTO(
                        topic.getName(),
                        topic.getPrompt(),
                        topic.getOrderNumber()))
                .collect(Collectors.toList());
        
        return new CourseDTO(course.getName(), topicDTOs);
    }
}
