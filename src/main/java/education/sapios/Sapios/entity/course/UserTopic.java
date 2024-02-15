package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_topics")
public class UserTopic {
    @EmbeddedId
    private UserTopicId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("topicId")
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    public UserTopicId getId() {
        return id;
    }

    public void setId(UserTopicId id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
