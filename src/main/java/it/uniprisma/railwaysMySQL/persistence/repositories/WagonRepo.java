package it.uniprisma.railwaysMySQL.persistence.repositories;


import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Route;
import it.uniprisma.railwaysMySQL.persistence.DAO.Wagon.Wagon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WagonRepo extends JpaRepository<Wagon, Integer> {
    @Query("select w from Wagon w where " +
            "((:trainID is null or w.train.id = :trainID) and " +
            "(:wagonType is null or w.wagonType = :wagonType) and " +
            "(:kitchenType is null or treat(w as restaurant_wagon).kitchenType = :kitchenType) and " +
            "(:fuelType is null or treat(w as motor_wagon).fuelType = :fuelType) and " +
            "(:bathroom is null or w.bathroom = :bathroom) and " +
            "(:wagonClass is null or w.wagonClass = :wagonClass) and " +
            "(:bedType is null or treat(w as bed_wagon).bedType = :bedType) and " +
            "(:minimumTables is null or treat(w as restaurant_wagon).nTables >= :minimumTables) and " +
            "(:minimumBeds is null or treat(w as bed_wagon).nBeds >= :minimumBeds) and " +
            "(:minimumSeats is null or treat(w as passenger_wagon ).nSeats >= :minimumSeats))")
    Page<Wagon> findAllWagonsForTrainWithFilters(
            @Param("trainID") Integer trainId, @Param("wagonType") WagonType wagonType, @Param("kitchenType") String kitchenType,
            @Param("fuelType") FuelType fuelType, @Param("bathroom") Boolean bathroom, @Param("wagonClass") WagonClass wagonClass,
            @Param("bedType") String bedType, @Param("minimumTables") Integer minimumTables, @Param("minimumBeds") Integer minimumBeds,
            @Param("minimumSeats") Integer minimumSeats, Pageable pageable);

    @Query("select w from Wagon w where " +
            "((:wagonType is null or w.wagonType = :wagonType) and " +
            "(:kitchenType is null or treat(w as restaurant_wagon).kitchenType = :kitchenType) and " +
            "(:fuelType is null or treat(w as motor_wagon).fuelType = :fuelType) and " +
            "(:bathroom is null or w.bathroom = :bathroom) and " +
            "(:wagonClass is null or w.wagonClass = :wagonClass) and " +
            "(:bedType is null or treat(w as bed_wagon).bedType = :bedType) and " +
            "(:minimumTables is null or treat(w as restaurant_wagon).nTables >= :minimumTables) and " +
            "(:minimumBeds is null or treat(w as bed_wagon).nBeds >= :minimumBeds) and " +
            "(:minimumSeats is null or treat(w as passenger_wagon ).nSeats >= :minimumSeats))")
    Page<Wagon> findAllWagonsWithFilters(
            @Param("bathroom") Boolean bathroom, @Param("wagonClass") WagonClass wagonClass, @Param("wagonType") WagonType wagonType,
            @Param("minimumTables") Integer minimumTables, @Param("kitchenType") String kitchenType, @Param("fuelType") FuelType fuelType,
            @Param("minimumBeds") Integer minimumBeds, @Param("bedType") String bedType, @Param("minimumSeats") Integer minimumSeats,
            Pageable pageable);

}
