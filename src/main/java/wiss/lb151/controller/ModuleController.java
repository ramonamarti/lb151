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

@Controller
@RequestMapping("/grademanager/module")
public class ModuleController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private ModuleService moduleService;

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

    @PostMapping("/check")
    public String checkModuleE(@Valid @ModelAttribute Module module, Model model, HttpSession session) {
        Student student = studentService.getStudent((String) session.getAttribute("user"));
        int index = 0;
        for (Module m : student.getModules()) {
            if (m.getId() == module.getId()) {
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

    public static void moduleInfo(Model model, Student student) {
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

