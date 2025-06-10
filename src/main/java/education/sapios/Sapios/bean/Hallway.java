package education.sapios.Sapios.bean;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.repository.CourseRepository;
import education.sapios.Sapios.repository.TopicRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class Hallway implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CourseRepository courseRepository;

    @Inject
    private TopicRepository topicRepository;

    private List<Course> courses;
    private List<Topic> topics;

    private Course course;
    private Topic topic;

    @Inject
    private Classroom classroom;

    @PostConstruct
    public void init() {
        courses = courseRepository.findAll();
        if (!courses.isEmpty()) {
            course = courses.getFirst(); // Set the default course
            onCourseChange(); // Populate topics for the default course
        } else {
            topics = new ArrayList<>();
        }
    }

    public void onCourseChange() {
        if (course != null) {
            topics = topicRepository.findByCourse(course);
        } else {
            topics = new ArrayList<>();
        }
    }

    public String submit() {
        if (topic != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("topic", topic);
        }
        return "clase?faces-redirect=true";
    }

    public List<Course> getCourses() {
        courses = courseRepository.findAll();
        return courses;
    }

    public List<Topic> getTopics() {
        if (topics == null) {
            topics = new ArrayList<>();
        }
        return topics;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
