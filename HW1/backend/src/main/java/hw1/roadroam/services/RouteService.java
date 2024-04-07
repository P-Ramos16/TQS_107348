package hw1.roadroam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hw1.roadroam.models.Route;
import hw1.roadroam.repositories.RouteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepo routeRepository;

    public Route save(Route c) {
        return routeRepository.save(c);
    }

    public List<Route> listRoutes() {
        return routeRepository.findAll();
    }

    public List<Route> listRoutesByOriginCity(Long origin) {
        return routeRepository.findByOriginId(origin);
    }

    public Route getRoute(Long id) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            return route.get();
        }
        else {
            return null;
        }
    }
}
