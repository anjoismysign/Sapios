package education.sapios.Sapios.entity.hallway;

public enum Courses {
    HISTORIA_DE_LA_CULTURA("Historia de la Cultura");

    private final String name;

    Courses(String name) {
        this.name = name;
    }

    public Course get() {
        Course course = new Course();
        course.setName(name);
        return course;
    }
}
