package education.sapios.Sapios.repository;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.entity.hallway.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findFirstByName(String name);

    default List<Course> list() {
        List<Course> all = new ArrayList<>();
        all.addAll(findAll());

        all.add(Courses.HISTORIA_DE_LA_CULTURA.get());

        return all;
    }
}
