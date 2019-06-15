package wiss.lb151.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wiss.lb151.model.Student;

import java.util.Optional;

/**
 * repository to find a student with the id or login name
 */
@Repository
public interface StudentRespository extends
        JpaRepository<Student,Integer>, CrudRepository<Student,Integer> {
    Optional<Student> findById(Long id);
    Optional<Student> findByLogin(String login);

}
