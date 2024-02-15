package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class UserTopicId {
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private Topic topic;

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
}
