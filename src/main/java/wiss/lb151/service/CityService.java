package wiss.lb151.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wiss.lb151.model.City;
import wiss.lb151.repository.CityRespository;

import javax.validation.Valid;
import java.util.Optional;

/***
 * service to add a city, get a city with its name or remove a city
 */
@Service
public class CityService {
    private final CityRespository cityRespository;

    public CityService(CityRespository cityRespository) {
        this.cityRespository = cityRespository;
    }

    /**
     * to add and save a city in the table city
     * @param c: city to save
     * @return id of the entry
     */
    public Long addCity(@Valid City c){
        return cityRespository.saveAndFlush(c).getId();
    }

    /**
     * to get a city from the table city
     * @param id: id of a city
     * @return city of the table city
     */
    public City getCity(Long id) {
        City city = new City();
        if(id != null){
            Optional<City> optionalCity = cityRespository.findById(id);
            if(optionalCity.isPresent())
                city = optionalCity.get();
        }
        return city;
    }

    /**
     * to get a city from the table city
     * @param name: name of a city
     * @return city of the table city
     */
    public City getCity(String name) {
        City city = new City();
        if(name != null){
            Optional<City> optionalCity = cityRespository.findByName(name);
            if(optionalCity.isPresent())
                city = optionalCity.get();
        }
        return city;
    }

    /**
     * to delete a city from the table city
     * @param id: id of a city
     */
    public void removeCity(Long id){
        if(id != null){
            Optional<City> optionalCity = cityRespository.findById(id);
            if(optionalCity.isPresent())
                cityRespository.deleteById(Math.toIntExact(id));
        }
    }
}
