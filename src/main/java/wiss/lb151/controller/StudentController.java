package wiss.lb151.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wiss.lb151.model.City;
import wiss.lb151.model.Exam;
import wiss.lb151.model.Module;
import wiss.lb151.model.Student;
import wiss.lb151.model.enums.*;
import wiss.lb151.service.CityService;
import wiss.lb151.service.StudentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * controller to manage the pages of the category student
 */
@Controller
@RequestMapping("/grademanager/student")
public class StudentController {
    private final StudentService studentService;

    private final CityService cityService;

    public StudentController(StudentService studentService, CityService cityService) {
        this.studentService = studentService;
        this.cityService = cityService;
    }

    /**
     * shows student data for logged in student
     * @param model: model for thymeleaf
     * @param session: to get student with login name
     * @return html document student with current student data
     */
    @GetMapping
    public String showStudent(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute(student);
        studentInfo(model);
        return "student";
    }

    /**
     * shows form to add new student
     * @param student: student to save and add
     * @param model: model for thymeleaf
     * @param session: to get student with login name
     * @return html document check student to show created data
     */
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute Student student, Model model, HttpSession session) {
        City city = cityService.getCity(student.getCity().getName());
        if (city != null && city.getName() != null) {
            student.setCity(city);
        }
        studentService.addStudent(student);
        session.setAttribute("user", student.getLogin());
        model.addAttribute(student);
        studentInfo(model);
        return "checkStudent"; // all from templates
    }

    /**
     * shows form to edit logged in student and fills in current data
     * @param model: model for thymeleaf
     * @param session: to get student with login name
     * @return html document edit student with filled in data
     */
    @GetMapping("/edit")
    public String showE(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute(student);
        studentInfo(model);
        return "editStudent";
    }

    /**
     * saves student in table student and shows edited data
     * @param student: student to save
     * @param model: model for thymeleaf
     * @param session: to get student with login name
     * @return html document check student of edited or created student
     */
    @PostMapping("/check")
    public String checkE(@Valid @ModelAttribute Student student, Model model, HttpSession session) {
        model.addAttribute("student", student);
        student.setModules(studentService.getStudent((String) session.getAttribute("user")).getModules());
        student.setGrades(studentService.getStudent((String) session.getAttribute("user")).getGrades());
        studentService.addStudent(student);
        session.setAttribute("user",student.getLogin());
        studentInfo(model);
        return "checkStudent";
    }

    /**
     * shows home after student and students other data is deactivated
     * @param model: model for thymeleaf
     * @param session: to get student with login name
     * @return html document home
     */
    @GetMapping("/del")
    public String showExamD(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        student.setDeactivated(Bool.Ja);
        // delete only when no other student uses
        //student.getCity().setDeactivated(Bool.Ja);
        List<Module> modules = student.getModules();
        for (Module m : modules) {
            m.setDeactivated(Bool.Ja);
            for (Exam e : m.getExams()) {
                e.setDeactivated(Bool.Ja);
                e.getGrade().setDeactivated(Bool.Ja);
            }
        }
        studentService.addStudent(student);
        model.addAttribute(student);
        return "home";
    }

    /**
     * adds lists to model
     * @param model: model for thymeleaf
     */
    static void studentInfo(Model model) {
        Direction[] directions = Direction.values();
        model.addAttribute(directions);
        Bool[] bools = Bool.values();
        model.addAttribute(bools);
    }
}

