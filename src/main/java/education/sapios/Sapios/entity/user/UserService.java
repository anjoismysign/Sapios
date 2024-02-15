package education.sapios.Sapios.entity.user;

import education.sapios.Sapios.entity.course.SapioUserCourse;
import education.sapios.Sapios.entity.course.UserCourse;
import education.sapios.Sapios.entity.course.UserCourseRepository;
import education.sapios.Sapios.entity.course.UserUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserCourseRepository userCourseRepository;
    private final UserUnitRepository userUnitRepository;

    @Autowired
    public UserService(UserCourseRepository userCourseRepository,
                       UserUnitRepository userUnitRepository) {
        this.userCourseRepository = userCourseRepository;
        this.userUnitRepository = userUnitRepository;
    }

    public List<SapioUserCourse> getCoursesForUser(UserEntity user) {
        List<UserCourse> userCourses = userCourseRepository.findByUser(user);
        return userCourses.stream()
                .map(SapioUserCourse::of)
                .collect(Collectors.toList());
    }
}
