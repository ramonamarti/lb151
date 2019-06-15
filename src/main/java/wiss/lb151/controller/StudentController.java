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

@Controller
@RequestMapping("/grademanager/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public String showStudent(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute(student);
        studentInfo(model);
        return "student";
    }

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

    @GetMapping("/edit")
    public String showE(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute(student);
        studentInfo(model);
        return "editStudent";
    }

    @PostMapping("/check")
    public String checkE(@Valid @ModelAttribute Student student, Model model, HttpSession session) {
        model.addAttribute("student", student);
        student.setModules(studentService.getStudent((String) session.getAttribute("user")).getModules());
        student.setGrades(studentService.getStudent((String) session.getAttribute("user")).getGrades());
//        student.setPassword(studentService.getStudent((String) session.getAttribute("user")).getPassword());
        studentService.addStudent(student);
        session.setAttribute("user",student.getLogin());
        studentInfo(model);
        return "checkStudent";
    }

    @GetMapping("/del")
    public String showExamD(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        student.setDeactivated(Bool.Ja);
        // löschen wenn kein anderer user angehaengt
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

    public static void studentInfo(Model model) {
        Direction[] directions = Direction.values();
        model.addAttribute(directions);
        Bool[] bools = Bool.values();
        model.addAttribute(bools);
    }
}

