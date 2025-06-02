package education.sapios.Sapios.entity.hallway;

import education.sapios.Sapios.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException; // Or other relevant exception
import org.springframework.transaction.TransactionSystemException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseEntityTests {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void testSaveCourseWithoutId() {
        Course course = new Course();
        course.setName("Test Course Without ID");
        // ID is not set, so it should be null by default for an object type.

        // We expect an exception when trying to save an entity with a null ID,
        // as ID is a primary key and should be non-nullable.
        // Spring/JPA might throw InvalidDataAccessApiUsageException or TransactionSystemException (wrapping a ConstraintViolationException)
        assertThrows(Exception.class, () -> {
            courseRepository.save(course);
            courseRepository.flush(); // Ensure changes are sent to DB for constraints to be checked
        });
    }

    @Test
    void testSaveCourseWithId() {
        String courseId = "COURSE_JUNIT_101";
        Course course = new Course();
        course.setId(courseId);
        course.setName("Test Course With ID");

        Course savedCourse = courseRepository.save(course);
        assertNotNull(savedCourse);
        assertEquals(courseId, savedCourse.getId());

        Course retrievedCourse = courseRepository.findById(courseId).orElse(null);
        assertNotNull(retrievedCourse);
        assertEquals(courseId, retrievedCourse.getId());
        assertEquals("Test Course With ID", retrievedCourse.getName());

        // Clean up - optional, but good practice in tests modifying state
        courseRepository.deleteById(courseId);
    }
}
