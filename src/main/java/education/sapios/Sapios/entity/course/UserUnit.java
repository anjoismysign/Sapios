package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_units")
public class UserUnit {
    @EmbeddedId
    private UserUnitId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("unitId")
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    public UserUnitId getId() {
        return id;
    }

    public void setId(UserUnitId id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
