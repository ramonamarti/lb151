package wiss.lb151.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wiss.lb151.model.Exam;
import wiss.lb151.repository.ExamRespository;

import javax.validation.Valid;
import java.util.Optional;

/**
 * service to add a exam, get a exam with its id or remove a exam
 */
@Service
public class ExamService {
    @Autowired
    private ExamRespository examRespository;

    /**
     * to add and save a exam in the table exam
     * @param e: exam to save
     * @return id of the entry
     */
    public Long addExam(@Valid Exam e){
        return examRespository.saveAndFlush(e).getId();
    }

    /**
     * to get a exam form the table exam with a id
     * @param id: id of a exam
     * @return exam with the correct id from the table exam
     */
    public Exam getExam(Long id){
        Exam exam = new Exam();
        if(id != null){
            Optional<Exam> optionalExam = examRespository.findById(id);
            if(optionalExam.isPresent())
                exam = optionalExam.get();
        }
        return exam;
    }

    public void removeExam(Long id){
        if(id != null){
            Optional<Exam> optionalExam = examRespository.findById(id);
            if(optionalExam.isPresent())
                examRespository.deleteById(Math.toIntExact(id));
        }
    }
}
