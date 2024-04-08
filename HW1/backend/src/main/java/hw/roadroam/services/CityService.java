package hw.roadroam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hw.roadroam.models.City;
import hw.roadroam.repositories.CityRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepository;

    public CityService(CityRepo cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City save(City c) {
        return cityRepository.save(c);
    }

    public List<City> listCities() {
        return cityRepository.findAll();
    }

    public City getCity(Long id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return city.get();
        }
        else {
            return null;
        }
    }
}
