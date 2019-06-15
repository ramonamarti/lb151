package wiss.lb151.model;

import wiss.lb151.model.enums.Bool;
import wiss.lb151.model.enums.Direction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * model student with an id, a family name, a first name, a login, a password, a street, a city,
 * a direction, a list of module, a list of grades and a deactivated-state
 */
@Entity(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_student")
    @SequenceGenerator(name="seq_student",allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    private String familyname;

    @NotEmpty
    @NotNull
    private String firstname;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String login;

    @NotEmpty
    @NotNull
    private String password;

    @NotNull
    @NotEmpty
    private String street;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentCityFk")
    private City city;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentModuleFk")
    private List<Module> modules = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentGradeFk")
    private List<Grade> grades = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Bool deactivated;

    public Student() {
    }

    public Student(String familyname, String firstname, String login) {
        this.familyname = familyname;
        this.firstname = firstname;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Bool getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Bool deactivated) {
        this.deactivated = deactivated;
    }
}
