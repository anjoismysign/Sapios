package education.sapios.Sapios.entity.course;

public interface SapioUserCourse {
    static SapioUserCourse of(UserCourse userCourse) {
        return new SapioUserCourse() {
            @Override
            public String getName() {
                return userCourse.getCourse().getName();
            }

            @Override
            public String getDescription() {
                return userCourse.getCourse().getDescription();
            }

            @Override
            public String getRole() {
                return userCourse.getRole().name();
            }
        };
    }

    String getName();

    String getDescription();

    String getRole();
}
