package wiss.lb151.model;

import wiss.lb151.model.enums.Bool;
import wiss.lb151.model.enums.Direction;
import wiss.lb151.model.enums.State;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * model module with an id, a number, a name, a direction, a state, a teacher, a list of exams and a deactivated-state
 */
@Entity(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_module")
    @SequenceGenerator(name = "seq_module", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Integer number;

    @NotNull
    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Enumerated(EnumType.STRING)
    private State state = State.Offen;

    @NotEmpty
    private String teacher;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ModuleExamFk")
    private List<Exam> exams;

    @Enumerated(EnumType.STRING)
    private Bool deactivated;

    public Module() {
    }

    public Module(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    /**
     * method to calculate the average of all grades of an module
     * used in thymeleaf module.html
     * @return double: grade value of the average
     */
    public double getAverage() {
        double grade = 0;
        double div = 0;
        for (Exam e : exams) {
            if (e.getDeactivated().toString().equals("Nein")) {
                grade += e.getGrade().getWeightGrade();
                div += e.getGrade().getWeight();
            }
        }
        if (div > 0)
            return Math.round(100 * (grade / div)) / 100d;
        return 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public Bool getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Bool deactivated) {
        this.deactivated = deactivated;
    }
}
