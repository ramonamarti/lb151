package wiss.lb151.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wiss.lb151.model.Grade;

import java.util.Optional;

/**
 * repository to find a grade with the id
 */
@Repository
public interface GradeRespository extends
        JpaRepository<Grade,Integer>, CrudRepository<Grade,Integer> {
    Optional<Grade> findById(Long id);
}
