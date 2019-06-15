package wiss.lb151.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wiss.lb151.model.Exam;
import wiss.lb151.model.Grade;
import wiss.lb151.model.Module;
import wiss.lb151.model.Student;
import wiss.lb151.model.enums.*;
import wiss.lb151.service.ExamService;
import wiss.lb151.service.ModuleService;
import wiss.lb151.service.StudentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/***
 * controller to manage the pages of the category exam
 */
@Controller
@RequestMapping("/grademanager/exam")
public class ExamController {
    private final StudentService studentService;

    private final ModuleService moduleService;

    private final ExamService examService;

    public ExamController(StudentService studentService, ModuleService moduleService, ExamService examService) {
        this.studentService = studentService;
        this.moduleService = moduleService;
        this.examService = examService;
    }

    /**
     * shows exam data for current module
     * @param id: to get module in students module list
     * @param model: model for thymeleaf
     * @param session: to get logged in student
     * @return html document exam without deactivated exams of current module
     */
    @GetMapping
    public String showExam(@Valid Long id, Model model, HttpSession session) {
        session.setAttribute("module", id);
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        for (Module m : student.getModules()) {
            if (m.getId().equals(id)) {
                model.addAttribute(m);
                model.addAttribute("number", m.getNumber());
                examInfo(model, m);
            }
        }
        return "exam";
    }

    /**
     * shows exam data for every module
     * @param model: model for thymeleaf
     * @param session: to get logged in student
     * @return html document exam without deactivated exams of all modules
     */
    @GetMapping("/all")
    public String showExamA(Model model, HttpSession session) {
        session.removeAttribute("module");
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute(student);
        model.addAttribute("exam", new Exam());
        return "examAll";
    }

    /**
     * shows form to add new module
     * @param model: model for thymeleaf
     * @param session: to get logged in student and current module
     * @return html document edit exam with empty form
     */
    @GetMapping("/add")
    public String showExamN(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        Module module = moduleService.getModule((Long) session.getAttribute("module"));
        model.addAttribute("idModule", session.getAttribute("module"));
        Exam exam = new Exam();
        model.addAttribute(student);
        model.addAttribute(exam);
        examInfo(model, module);
        return "editExam";
    }

    /**
     * shows form to edit exam and fills in current data
     * @param id: to get exam in modules exam list
     * @param model: model for thymeleaf
     * @param session: to get logged in student and current module
     * @return html document edit exam with filled in data
     */
    @GetMapping("/edit")
    public String showExamE(@Valid Long id, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        Module module = moduleService.getModule((Long) session.getAttribute("module"));
        Exam exam = examService.getExam(id);
        model.addAttribute("idModule", session.getAttribute("module"));
        model.addAttribute("idExam", id);
        List<Exam> exams = module.getExams();
        exams.add(exam);
        module.setExams(exams);
        model.addAttribute(module);
        model.addAttribute(exam);
        examInfo(model, module);
        return "editExam";
    }

    /**
     * saves exam in table exam and shows edited data
     * @param exam: exam to save
     * @param model: model for thymeleaf
     * @param session: to get logged in student and current module
     * @return html document check exam for edited or created exams
     */
    @PostMapping("/check")
    public String checkExamE(@Valid @ModelAttribute Exam exam, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        Module module = moduleService.getModule((Long) session.getAttribute("module"));
        for (Module m : student.getModules()) {
            if (m.getId() == session.getAttribute("module")) {
                model.addAttribute(m);
                model.addAttribute("number", m.getNumber());
                examInfo(model, m);
            }
        }
        int index = 0;
        for (Exam e : module.getExams()) {
            if (e.getId().equals(exam.getId())) {
                break;
            }
            index++;
        }
        if (module.getExams().size() > index) {
            module.getExams().set(index, exam);
        } else {
            module.getExams().add(exam);
        }
        model.addAttribute(student);
        model.addAttribute("nameModule", module.getName());
        model.addAttribute("idModule", session.getAttribute("module"));
        model.addAttribute(exam);
        Double grade = exam.getGrade().getValue();
        if (grade >= 5) {
            setGradeColor(module,index,Color.Gruen);
        } else if (grade >= 4) {
            setGradeColor(module,index,Color.Schwarz);
        } else {
            setGradeColor(module,index,Color.Rot);
        }
        List<Grade> grades = student.getGrades();
        grades.add(exam.getGrade());
        student.setGrades(grades);
        exam.getGrade().setDeactivated(Bool.Nein);
        studentService.addStudent(student);
        examInfo(model, module);
        return "checkExam";
    }

    /**
     * deactivates exam and exams other data
     * @param id: to get exam in modules exam list
     * @param model: model for thymeleaf
     * @param session: to get logged in student and current module
     * @return html document exam with current module without deactivated exams
     */
    @GetMapping("/del")
    public String showExamD(@Valid Long id, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        for (Module m : student.getModules()) {
            if (m.getId() == session.getAttribute("module")) {
                for (Exam e : m.getExams()) {
                    if (e.getId().equals(id)) {
                        e.setDeactivated(Bool.Ja);
                        e.getGrade().setDeactivated(Bool.Ja);
                    }
                }
                examInfo(model, m);
            }
        }
        model.addAttribute(student);
        studentService.addStudent(student);
        return "exam";
    }

    /**
     * adds lists to model
     * @param model: model for thymeleaf
     * @param module: module to get exams
     */
    private static void examInfo(Model model, Module module) {
        State[] states = State.values();
        model.addAttribute(states);
        Type[] examTypes = Type.values();
        model.addAttribute(examTypes);
        Color[] colors = Color.values();
        model.addAttribute(colors);
        List<Exam> exams = module.getExams();
        model.addAttribute(exams);
        Bool[] bools = Bool.values();
        model.addAttribute(bools);
    }

    /**
     * sets the color of a grade
     * @param module: module of the grade
     * @param index: index of the exam in the module list
     * @param color: color to define the grade on
     */
    private void setGradeColor(Module module, int index, Color color){
        module.getExams().get(index).getGrade().setColor(color);
    }
}

