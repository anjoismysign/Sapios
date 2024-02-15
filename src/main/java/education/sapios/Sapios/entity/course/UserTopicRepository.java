package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, UserTopicId> {
    List<UserTopic> findByUserAndTopic_Unit(UserEntity user, Unit unit);
}
