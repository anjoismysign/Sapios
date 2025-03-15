package education.sapios.Sapios.entity.hallway;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 15000)
    private String prompt;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private int orderNumber;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String display(){
        return getCourse().getName() + " - " + getName();
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSystemPrompt(){
        return """
            You are a patient and knowledgeable tutor specialized in "%s".
            Below is the teaching material for today's lesson:
            
            %s
            
            When a student asks a question, answer using only the information in the above material.
            Explain your answer step-by-step.
            """.formatted(course.getName(), prompt);
    }

    /**
     * String teachingContent = """
     *             Introduction to Calculus:
     *             - A derivative represents the rate of change of a function.
     *             - For example, the derivative of f(x) = x^2 is 2x, which gives the slope of the tangent line at any point on the curve.
     *             """;
     *
     *         // Create the system prompt that incorporates the teaching content using a text block and formatted string
     *         String systemPrompt = """
     *             You are a patient and knowledgeable tutor specialized in Calculus.
     *             Below is the teaching material for today's lesson:
     *
     *             %s
     *
     *             When a student asks a question, answer using only the information in the above material.
     *             Explain your answer step-by-step.
     *             """.formatted(teachingContent);
     */
}
