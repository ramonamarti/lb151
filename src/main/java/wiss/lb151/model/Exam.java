package wiss.lb151.model;

import wiss.lb151.model.enums.Bool;
import wiss.lb151.model.enums.State;
import wiss.lb151.model.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * model exam with an id, an type, a state, a grade and a deactivate-state
 */
@Entity(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_exam")
    @SequenceGenerator(name="seq_exam",allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type = Type.Zwischenprüfung;

    @Enumerated(EnumType.STRING)
    private State state = State.Offen;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "examGradeFk")
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private Bool deactivated;

    public Exam() {
    }

    public Exam(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Bool getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Bool deactivated) {
        this.deactivated = deactivated;
    }
}
