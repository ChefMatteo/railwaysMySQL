package it.uniprisma.railwaysMySQL.services;

import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.models.enums.TrainType;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Route;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import it.uniprisma.railwaysMySQL.persistence.DAO.Wagon.Wagon;
import it.uniprisma.railwaysMySQL.persistence.repositories.RouteRepo;
import it.uniprisma.railwaysMySQL.persistence.repositories.TrainRepo;
import it.uniprisma.railwaysMySQL.persistence.repositories.WagonRepo;
import it.uniprisma.railwaysMySQL.utils.ConflictException;
import it.uniprisma.railwaysMySQL.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainService {
    private final TrainRepo trainRepo;
    private final WagonRepo wagonRepo;
    private final RouteRepo routeRepo;

    public TrainService(TrainRepo trainRepo, WagonRepo wagonRepo, RouteRepo routeRepo) {
        this.trainRepo = trainRepo;
        this.wagonRepo = wagonRepo;
        this.routeRepo = routeRepo;
    }


    public Train createNewTrain(Train train) {
        return trainRepo.save(train);
    }

    public Page<Train> findTrains(String companyName, TrainType trainType, Pageable pageable) {
        return trainRepo.findAllWithFilters(companyName, trainType, pageable);
    }

    public Train getSingleTrain(int trainId) {
        return trainRepo.findById(trainId).orElseThrow(()->new NotFoundException("Train", trainId));
    }

    public Train updateSingleTrain(Train trainsChanges, Integer trainId) {
        Train trainToUpdate = trainRepo.findById(trainId).orElseThrow(()->new NotFoundException("Train", trainId));
        updateTrain(trainToUpdate, trainsChanges);
        return trainRepo.save(trainToUpdate);
    }

    public void deleteTrain(Integer trainId) {
        try {
            trainRepo.deleteById(trainId);
        }catch (Exception e){
            throw new NotFoundException("Train", trainId);
        }
    }

    public Page<Wagon> findWagonsOfTrainPage(Integer trainId, WagonType wagonType, String kitchenType, FuelType fuelType,
                                             Boolean bathroom, WagonClass wagonClass, String bedType,
                                             Integer minimumBeds, Integer minimumTables, Integer minimumSeats,
                                             Pageable pageable) {
        return wagonRepo.findAllWagonsForTrainWithFilters(trainId, wagonType, kitchenType, fuelType, bathroom, wagonClass, bedType, minimumBeds, minimumTables, minimumSeats, pageable);
    }
    public Page<Route> findRoutesOfTrainPage(Integer trainId, String startStation, String destinationStation, String binaryType, Double minimumLength,
                                             Double maxLength, Pageable pageable) {
        return routeRepo.findAllRouteForTrainWithFilters(trainId, startStation, destinationStation,
                                                         binaryType, minimumLength, maxLength, pageable);
    }

    public void createTrainRouteAssociation(Integer trainId, Integer routeId) {
        Train trainToUpdate = trainRepo.findById(trainId).orElseThrow(()->new NotFoundException("Train", trainId));
        Route route = routeRepo.findById(routeId).orElseThrow(()->new NotFoundException("Route", routeId));
        if(trainToUpdate.getRoutes().add(route)){
            trainRepo.save(trainToUpdate);
        }
        else {
            System.out.println(Thread.currentThread().getStackTrace()[0].getFileName()+" "+Thread.currentThread().getStackTrace()[0].getLineNumber());
            throw new ConflictException("Connection already exist");
        }
    }

    public void deleteTrainRouteAssociation(Integer trainId, Integer routeId) {
        Train trainToUpdate = trainRepo.findById(trainId).orElseThrow(()->new NotFoundException("Train", trainId));
        Route route = routeRepo.findById(routeId).orElseThrow(()->new NotFoundException("Route", routeId));
        if(trainToUpdate.getRoutes().remove(route)){
            trainRepo.save(trainToUpdate);
        }
        else throw new ConflictException("Connection doesn't exist");

    }

    public void createOrUpdateTrainWagonAssociation(Integer trainId, Integer wagonId) {
        Train train = trainRepo.findById(trainId).orElseThrow(()->new NotFoundException("Train", trainId));
        Wagon wagon = wagonRepo.findById(wagonId).orElseThrow(()->new NotFoundException("Wagon", wagonId));
        wagon.setTrain(train);
        wagonRepo.save(wagon);

    }

    public void updateTrain(Train trainToUpdate, Train trainsChanges){
        if(trainsChanges.getType()!=null)
            trainToUpdate.setType(trainsChanges.getType());
        if(trainsChanges.getCompany()!=null)
            trainToUpdate.setCompany(trainsChanges.getCompany());
    }
}
