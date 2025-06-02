package education.sapios.Sapios.bean;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.repository.CourseRepository;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class CourseBuilder implements Serializable {

    private String id;
    private String name;

    @Autowired
    private CourseRepository courseRepository;

    public void addCourse() {
        if (name == null || name.trim().isEmpty() || id == null || id.trim().isEmpty()) {
            return;
        }

        Course course = new Course();
        course.setId(this.id);
        course.setName(this.name);
        courseRepository.save(course);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("editorial");
        } catch ( IOException exception ) {
            exception.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
