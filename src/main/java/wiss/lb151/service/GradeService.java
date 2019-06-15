package wiss.lb151.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wiss.lb151.model.Grade;
import wiss.lb151.repository.GradeRespository;

import javax.validation.Valid;
import java.util.Optional;

/**
 * service to add a grade, get a grade with its id or remove a grade
 */
@Service
public class GradeService {
    @Autowired
    private GradeRespository gradeRespository;

    /**
     * to add and save a grade in the table grade
     * @param g: grade to save
     * @return id of the entry
     */
    public Long addGrade(@Valid Grade g){
        return gradeRespository.saveAndFlush(g).getId();
    }

    /**
     * to get a grade form the table grade with a id
     * @param id: id of a grade
     * @return grade with the correct id from the table grade
     */
    public Grade getGrade(Long id) {
        Grade Grade = new Grade();
        if(id != null){
            Optional<Grade> optionalGrade = gradeRespository.findById(id);
            if(optionalGrade.isPresent())
                Grade = optionalGrade.get();
        }
        return Grade;
    }

    public void removeGrade(Long id){
        if(id != null){
            Optional<Grade> optionalGrade = gradeRespository.findById(id);
            if(optionalGrade.isPresent())
                gradeRespository.deleteById(Math.toIntExact(id));
        }
    }
}
