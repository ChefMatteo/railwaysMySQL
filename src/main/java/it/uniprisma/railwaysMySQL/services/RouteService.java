package it.uniprisma.railwaysMySQL.services;

import it.uniprisma.railwaysMySQL.persistence.DAO.Route;
import it.uniprisma.railwaysMySQL.persistence.repositories.RouteRepo;
import it.uniprisma.railwaysMySQL.utils.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    private final RouteRepo routeRepo;

    public RouteService(RouteRepo routeRepo) {
        this.routeRepo = routeRepo;
    }

    public Route createNewRoute(Route route) {
        return routeRepo.save(route);
    }

    public Page<Route> findRoutesPage(String destinationStationName, String startStationName, String binaryType,
                                      Double minimumLength, Double maxLength, Pageable pageable) {
        return routeRepo.findAllRouteWithFilters(binaryType, startStationName, destinationStationName,
                minimumLength, maxLength, pageable);

    }

    public Route getSingleRoute(Integer routeId) {
        return routeRepo.findById(routeId).orElseThrow(()->new NotFoundException("Route", routeId));
    }

    public Route updateSingleRoute(Route routeChanges, Integer routeId) {
        Route routeToUpdate = routeRepo.findById(routeId).orElseThrow(()->new NotFoundException("Route", routeId));
        updateRoute(routeToUpdate, routeChanges);
        return routeRepo.save(routeToUpdate);
    }

    public void deleteRoute(int routeId) {
        try {
            routeRepo.deleteById(routeId);
        }catch (Exception e){
            throw new NotFoundException("Route", routeId);
        }
    }

    //TODO reflection
    public void updateRoute(Route routeToUpdate, Route routeChanges){
        if(routeChanges.getBinaryType()!=null)
            routeToUpdate.setBinaryType(routeChanges.getBinaryType());
        if(routeChanges.getStartStation()!=null)
            routeToUpdate.setStartStation(routeChanges.getStartStation());
        if(routeChanges.getDestinationStation()!=null)
            routeToUpdate.setDestinationStation(routeChanges.getDestinationStation());
        if(routeChanges.getLength()!=null)
            routeToUpdate.setLength(routeChanges.getLength());
    }

}
