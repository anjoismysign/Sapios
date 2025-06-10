package education.sapios.Sapios.converter;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class CourseConverter implements Converter<Course> {

    @Inject
    private CourseRepository courseRepository;

    @Override
    public Course getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return courseRepository.findById(value).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Course course) {
        if (course == null) {
            return "";
        }

        return course.getId();
    }
}
