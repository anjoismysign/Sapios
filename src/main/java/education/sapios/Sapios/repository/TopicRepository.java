package education.sapios.Sapios.repository;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.entity.hallway.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCourse(Course course);

    default List<Topic> list() {
        List<Topic> all = new ArrayList<>();
        all.addAll(findAll());
        all.add(Topics.ONE.get());
        return all;
    }
}
