package wiss.lb151.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wiss.lb151.model.Exam;
import wiss.lb151.model.Module;
import wiss.lb151.model.Student;
import wiss.lb151.model.enums.*;
import wiss.lb151.service.ModuleService;
import wiss.lb151.service.StudentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * controller to manage the pages of the category module
 */
@Controller
@RequestMapping("/grademanager/module")
public class ModuleController {
    private final StudentService studentService;

    private final ModuleService moduleService;

    public ModuleController(StudentService studentService, ModuleService moduleService) {
        this.studentService = studentService;
        this.moduleService = moduleService;
    }

    /**
     * shows module data for logged in student
     * @param model: model for thymeleaf
     * @param session: to get logged in student
     * @return html document module without deactivated modules
     */
    @GetMapping
    public String showModule(Model model, HttpSession session) {
        Module module = new Module();
        session.setAttribute("module", module);
        session.removeAttribute("module");
        model.addAttribute(module);
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute(student);
        moduleInfo(model, student);
        return "module";
    }

    /**
     * shows form to add new module
     * @param model: model for thymeleaf
     * @param session: to get logged in student
     * @return html document edit module with empty form
     */
    @GetMapping("/add")
    public String showModuleN(Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute("id", (long) student.getModules().size());
        Module module = new Module();
        model.addAttribute(student);
        model.addAttribute(module);
        moduleInfo(model, student);
        return "editModule";
    }

    /**
     * shows form to edit module and fills in current data
     * @param id: to get module in students module list
     * @param model: model for thymeleaf
     * @param session: to get logged in student
     * @return html document edit module with filled in data
     */
    @GetMapping("/edit")
    public String showModuleE(@Valid Long id, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        model.addAttribute("idModule", id);
        Module module = moduleService.getModule(id);
        model.addAttribute(module);
        model.addAttribute(student);
        moduleInfo(model, student);
        return "editModule";
    }

    /**
     * saves module in table module and shows edited data
     * @param module: module to save
     * @param model: model for thymeleaf
     * @param session: to get logged in student
     * @return html document module check for edited or created module
     */
    @PostMapping("/check")
    public String checkModuleE(@Valid @ModelAttribute Module module, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        int index = 0;
        for (Module m : student.getModules()) {
            if (m.getId().equals(module.getId())) {
                break;
            }
            index++;
        }
        if (student.getModules().size() > index){
            student.getModules().set(index, module);
        } else{
            student.getModules().add(module);
        }
        model.addAttribute(student);
        model.addAttribute("module", module);
        studentService.addStudent(student);
        moduleInfo(model, student);
        return "checkModule";
    }

    /**
     * deactivates module and modules other data
     * @param id: to get module in students module list
     * @param model: model for thymeleaf
     * @param session: to get logged in student and remove module session
     * @return html document module without deactivated modules
     */
    @GetMapping("/del")
    public String showModuleD(@Valid Long id, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        Module module = student.getModules().get((int) (id-1));
        module.setDeactivated(Bool.Ja);
        for (Exam e : module.getExams()){
            e.setDeactivated(Bool.Ja);
            e.getGrade().setDeactivated(Bool.Ja);
        }
        session.removeAttribute("module");
        model.addAttribute(student);
        studentService.addStudent(student);
        moduleInfo(model,student);
        return "module";
    }

    /***
     * adds lists to model
     * @param model: model for thymeleaf
     * @param student: current student to get modules
     */
    static void moduleInfo(Model model, Student student) {
        State[] state = State.values();
        model.addAttribute(state);
        Direction[] directions = Direction.values();
        model.addAttribute(directions);
        Bool[] bools = Bool.values();
        model.addAttribute(bools);
        Bool no = Bool.Nein;
        model.addAttribute(no);
        List<Module> modules = student.getModules();
        model.addAttribute(modules);
    }
}

