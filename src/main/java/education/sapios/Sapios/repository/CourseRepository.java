package education.sapios.Sapios.repository;

import education.sapios.Sapios.entity.hallway.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findFirstByName(String name);
}
