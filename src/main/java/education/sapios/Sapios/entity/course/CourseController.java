package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import education.sapios.Sapios.entity.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserUnitRepository userUnitRepository;
    private final UnitRepository unitRepository;
    private final UserTopicRepository userTopicRepository;
    private final TopicRepository topicRepository;

    public CourseController(UserRepository userRepository,
                            CourseRepository courseRepository,
                            UserUnitRepository userUnitRepository,
                            UnitRepository unitRepository,
                            UserTopicRepository userTopicRepository,
                            TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userUnitRepository = userUnitRepository;
        this.unitRepository = unitRepository;
        this.userTopicRepository = userTopicRepository;
        this.topicRepository = topicRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Long id = course.getId();
        if (id != null && courseRepository.existsById(id) ||
                courseRepository.findFirstByName(course.getName()).isPresent())
            return ResponseEntity.badRequest().body(null);
        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.ok(savedCourse);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/get/{courseId}")
    public ResponseEntity<Course> findCourseById(@PathVariable Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return ResponseEntity.ok(course);
    }

    @PostMapping("/assign/{userId}/{courseId}")
    public ResponseEntity<String> assignCourseToUser(@PathVariable Long userId,
                                                     @PathVariable Long courseId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        List<UserUnit> userUnits = userUnitRepository.findByUserAndUnit_Course(user, course);
        if (!userUnits.isEmpty()) {
            return ResponseEntity.badRequest().body("User is already assigned to this course");
        }

        List<Unit> units = unitRepository.findByCourse(course);
        for (Unit unit : units) {
            List<Topic> topics = topicRepository.findByUnit(unit);
            UserUnit userUnit = new UserUnit();
            userUnit.setUser(user);
            userUnit.setUnit(unit);
            userUnit.setCompleted(false);
            userUnitRepository.save(userUnit);
            for (Topic topic : topics) {
                UserTopic userTopic = new UserTopic();
                userTopic.setUser(user);
                userTopic.setTopic(topic);
                userTopic.setCompleted(false);
                userTopicRepository.save(userTopic);
            }
        }

        return ResponseEntity.ok("Course assigned to user successfully");
    }
}
