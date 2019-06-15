package wiss.lb151.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wiss.lb151.model.Module;

import java.util.Optional;

/**
 * repository to find a module with the id
 */
@Repository
public interface ModuleRespository extends
        JpaRepository<Module,Integer>, CrudRepository<Module,Integer> {
    Optional<Module> findById(Long id);
}
