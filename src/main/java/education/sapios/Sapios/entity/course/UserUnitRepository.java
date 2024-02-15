package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserUnitRepository extends JpaRepository<UserUnit, UserUnitId> {
    List<UserUnit> findByUserAndUnit_Course(UserEntity user, Course course);
}
