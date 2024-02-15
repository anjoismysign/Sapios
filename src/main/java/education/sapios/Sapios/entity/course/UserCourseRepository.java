package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    List<UserCourse> findByUser(UserEntity user);
}
