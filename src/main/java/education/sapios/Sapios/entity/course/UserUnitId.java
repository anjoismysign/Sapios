package education.sapios.Sapios.entity.course;

import education.sapios.Sapios.entity.user.UserEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class UserUnitId implements Serializable {
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private Unit unit;

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
}
