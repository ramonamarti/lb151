package wiss.lb151.model;

import wiss.lb151.model.enums.Bool;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * model city with an id, a name and a zip
 */
@Entity(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_city")
    @SequenceGenerator(name="seq_city",allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    private String zip;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Bool deactivated;

    public City() {
    }

    public City(String zip, String name) {
        this.zip = zip;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bool getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Bool deactivated) {
        this.deactivated = deactivated;
    }
}
