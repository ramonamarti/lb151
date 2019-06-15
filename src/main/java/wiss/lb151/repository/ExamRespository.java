package wiss.lb151.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wiss.lb151.model.Exam;

import java.util.Optional;

/**
 * repository to find a exam with the id
 */
@Repository
public interface ExamRespository extends
        JpaRepository<Exam,Integer>, CrudRepository<Exam,Integer> {
    Optional<Exam> findById(Long id);
}
