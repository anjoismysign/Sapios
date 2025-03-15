package education.sapios.Sapios.bean;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.repository.CourseRepository;
import education.sapios.Sapios.repository.TopicRepository;
import jakarta.annotation.Nullable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TopicBuilder implements Serializable {

    private String name;
    private String prompt;
    private String course;
    private int order;
    private List<Course> courses;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void addTopic() {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        @Nullable Course course = courseRepository.findFirstByName(this.course).orElse(null);
        if (course == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No encontrado", "\"" + name + "\" no corresponde al nombre de un curso.")
            );
            return;
        }

        Topic topic = new Topic();
        topic.setName(name);
        topic.setPrompt(prompt);
        topic.setCourse(course);
        topic.setOrderNumber(order);

        topicRepository.save(topic);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("editorial");
        } catch ( IOException exception ) {
            exception.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        if (courses == null) {
            courses = courseRepository.findAll();
        }
        return courses;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

}