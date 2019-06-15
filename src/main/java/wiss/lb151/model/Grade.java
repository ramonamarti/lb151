package wiss.lb151.model;

import wiss.lb151.model.enums.Bool;
import wiss.lb151.model.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * model grade with an id, a value, a weight, a automatic color and a deactivated-state
 */
@Entity(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_grade")
    @SequenceGenerator(name="seq_grade",allocationSize = 1)
    private Long id;

    @NotNull
    private Double value = 4.0;

    @NotNull
    private Double weight = 1.0;

    @Enumerated(EnumType.STRING)
    private Color color = Color.Gruen;

    @Enumerated(EnumType.STRING)
    private Bool deactivated;

    public Grade() {
    }

    public Grade(Double value, Double weight) {
        this.value = value;
        this.weight = weight;
    }

    public Double getWeightGrade(){
        return value * weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Bool getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Bool deactivated) {
        this.deactivated = deactivated;
    }
}
