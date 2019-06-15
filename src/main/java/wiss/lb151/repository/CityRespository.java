package wiss.lb151.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wiss.lb151.model.City;

import java.util.Optional;

/**
 * repository to find a city with the id or city name
 */
@Repository
public interface CityRespository extends
        JpaRepository<City,Integer>, CrudRepository<City,Integer> {
    Optional<City> findById(Long id);
    Optional<City> findByName(String name);

}
