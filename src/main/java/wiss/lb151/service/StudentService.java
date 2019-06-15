package wiss.lb151.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wiss.lb151.model.Student;
import wiss.lb151.repository.StudentRespository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/***
 * service to add a student, get a student with its login name, get a student with its id or remove a student
 */
@Service
public class StudentService {
    private final StudentRespository studentRespository;

    public StudentService(StudentRespository studentRespository) {
        this.studentRespository = studentRespository;
    }

    /**
     * to add and save a student in the table student
     * @param s: student to save
     * @return id of the entry
     */
    public Long addStudent(@Valid Student s){
        return studentRespository.saveAndFlush(s).getId();
    }

    /**
     * to get a student form the table student with a login name
     * @param login: login of a student
     * @return student with the correct login name from the table student
     */
    public Student getStudent(String login){
        Student student = new Student();
        if(login != null){
            Optional<Student> optionalStudent = studentRespository.findByLogin(login);
            if(optionalStudent.isPresent())
                student = optionalStudent.get();
        }
        return student;
    }

    /**
     * to get a student form the table student with an id
     * @param id: id of a student
     * @return student with the correct id name from the table student
     */
    public Student getStudent(Long id){
        Student student = new Student();
        if(id != null){
            Optional<Student> optionalStudent = studentRespository.findById(id);
            if(optionalStudent.isPresent())
                student = optionalStudent.get();
        }
        return student;
    }

    /**
     * to delete a student from the table student
     * @param id: id of a student
     */
    public void removeStudent(Long id){
        if(id != null){
            Optional<Student> optionalStudent = studentRespository.findById(id);
            if(optionalStudent.isPresent()) {
                studentRespository.deleteById(Math.toIntExact(id));
            }
        }
    }

    /**
     * to get a list of all students from the table student
     * @return list of all students of the table student
     */
    // not used jet
    public List<Student> findStudent(){
        return studentRespository.findAll();
    }
}
