package education.sapios.Sapios.entity.course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Unit unit = topic.getUnit();
        Long id = topic.getId();
        if (id != null && topicRepository.existsById(id) ||
                unit != null && topicRepository.findByUnit(unit).stream()
                        .anyMatch(t -> t.getName().equals(topic.getName())))
            return ResponseEntity.badRequest().body(null);
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.ok(savedTopic);
    }

    @PostMapping("/delete")
    public ResponseEntity<Topic> deleteTopic(@RequestBody Topic topic) {
        Long id = topic.getId();
        if (id == null || !topicRepository.existsById(id))
            return ResponseEntity.badRequest().body(null);
        topicRepository.delete(topic);
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/findbyunit")
    public ResponseEntity<List<Topic>> findByUnit(@RequestBody Unit unit) {
        List<Topic> topics = topicRepository.findByUnit(unit);
        return ResponseEntity.ok(topics);
    }
}
