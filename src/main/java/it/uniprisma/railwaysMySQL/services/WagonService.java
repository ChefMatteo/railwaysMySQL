package it.uniprisma.railwaysMySQL.services;

import it.uniprisma.railwaysMySQL.models.WagonDOT;
import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import it.uniprisma.railwaysMySQL.persistence.DAO.*;
import it.uniprisma.railwaysMySQL.persistence.DAO.Wagon.*;
import it.uniprisma.railwaysMySQL.persistence.repositories.TrainRepo;
import it.uniprisma.railwaysMySQL.persistence.repositories.WagonRepo;
import it.uniprisma.railwaysMySQL.utils.BadRequestException;
import it.uniprisma.railwaysMySQL.utils.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WagonService {
    private final WagonRepo wagonRepo;
    private final TrainRepo trainRepo;

    public WagonService(TrainRepo trainRepo, WagonRepo wagonRepo) {
        this.trainRepo = trainRepo;
        this.wagonRepo = wagonRepo;
    }

    public Wagon createSingleWagon(WagonDOT wagonDOT) {
        Train train = trainRepo.findById(wagonDOT.getTrainId()).orElseThrow(()->new BadRequestException("Train ID is null or invalid"));
        return wagonRepo.save(Wagon.createWagon(train, wagonDOT));
    }

    public Page<Wagon> findWagonPage(Boolean bathroom, WagonClass wagonClass, WagonType wagonType, Integer minimumTables,
                                     String kitchenType, FuelType fuelType, Integer minimumBeds, String bedType,
                                     Integer minimumSeats, Pageable pageable) {
        return wagonRepo.findAllWagonsWithFilters(bathroom, wagonClass, wagonType, minimumTables,
                kitchenType, fuelType, minimumBeds, bedType, minimumSeats, pageable);
    }

    public Wagon getSingleWagon(Integer wagonId) {
        return wagonRepo.findById(wagonId).orElseThrow(() -> new NotFoundException("Wagon", wagonId));
    }

    public Wagon updateSingleWagon(WagonDOT wagonDOTChanges, Integer wagonId) {
        Wagon wagonToUpdate = wagonRepo.findById(wagonId).orElseThrow(()->new NotFoundException("Wagon", wagonId));
        updateWagon(wagonToUpdate, wagonDOTChanges);
        return wagonRepo.save(wagonToUpdate);
    }

    public void deleteSingleWagon(Integer wagonId) {
        try {
            wagonRepo.deleteById(wagonId);
        }catch (Exception e){
            throw new NotFoundException("Wagon", wagonId);
        }
    }


    public void updateWagon(Wagon wagonToUpdate, WagonDOT wagonDOTChanges){
        trainRepo.findById(wagonDOTChanges.getTrainId())
                .ifPresent(wagonToUpdate::setTrain);
        if(wagonDOTChanges.getBathroom()!=null)
            wagonToUpdate.setBathroom(wagonDOTChanges.getBathroom());
        if(wagonDOTChanges.getWagonClass()!=null)
            wagonToUpdate.setWagonClass(wagonDOTChanges.getWagonClass());
        if(wagonDOTChanges.getHeight()!=0)
            wagonToUpdate.setHeight(wagonDOTChanges.getHeight());
        if(wagonDOTChanges.getLength()!=0)
            wagonToUpdate.setLength(wagonDOTChanges.getLength());
        if(wagonDOTChanges.getWidth()!=0)
            wagonToUpdate.setWidth(wagonDOTChanges.getLength());
        if (BedWagon.class.equals(wagonToUpdate.getClass())) {
            if (wagonDOTChanges.getBedType() != null)
                ((BedWagon) wagonToUpdate).setBedType(wagonDOTChanges.getBedType());
            if (wagonDOTChanges.getNBeds() != 0)
                ((BedWagon) wagonToUpdate).setNBeds(wagonDOTChanges.getNBeds());
        } else if (MotorWagon.class.equals(wagonToUpdate.getClass())) {
            if (wagonDOTChanges.getFuelType() != null)
                ((MotorWagon) wagonToUpdate).setFuelType(wagonDOTChanges.getFuelType());
        } else if (PassengerWagon.class.equals(wagonToUpdate.getClass())) {
            if (wagonDOTChanges.getNSeats() != 0)
                ((PassengerWagon) wagonToUpdate).setNSeats(wagonDOTChanges.getNSeats());
        } else if (RestaurantWagon.class.equals(wagonToUpdate.getClass())) {
            if (wagonDOTChanges.getKitchenType() != null)
                ((RestaurantWagon) wagonToUpdate).setKitchenType(wagonDOTChanges.getKitchenType());
            if (wagonDOTChanges.getNBeds() != 0)
                ((RestaurantWagon) wagonToUpdate).setNTables(wagonDOTChanges.getNTables());
        }
    }


}
