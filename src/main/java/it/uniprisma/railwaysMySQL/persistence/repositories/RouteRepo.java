package it.uniprisma.railwaysMySQL.persistence.repositories;


import it.uniprisma.railwaysMySQL.persistence.DAO.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RouteRepo extends JpaRepository<Route, Integer> {

    @Query("select r from Route r join r.trains rt where " +
            "((:trainId is null or rt.id = :trainId) and " +
            "(:binaryType is null or r.binaryType = :binaryType) and " +
            "(:destinationStation is null or r.destinationStation = :destinationStation) and " +
            "(:startStation is null or r.startStation = :startStation) and " +
            "(:minimumLength is null or r.length >= :minimumLength) and " +
            "(:maxLength is null or r.length <= :maxLength))")
    Page<Route> findAllRouteForTrainWithFilters(
            @Param("trainId")Integer trainId, @Param("binaryType") String binaryType, @Param("startStation")String startStation,
            @Param("destinationStation")String destinationStation, @Param("minimumLength")Double minimumLength, @Param("maxLength")Double maxLength, Pageable pageable);

    @Query("select r from Route r where " +
            "((:binaryType is null or r.binaryType = :binaryType) and " +
            "(:destinationStation is null or r.destinationStation = :destinationStation) and " +
            "(:startStation is null or r.startStation = :startStation) and " +
            "(:minimumLength is null or r.length >= :minimumLength) and " +
            "(:maxLength is null or r.length <= :maxLength))")
    Page<Route> findAllRouteWithFilters(
            @Param("binaryType") String binaryType, @Param("startStation")String startStation,
            @Param("destinationStation")String destinationStation, @Param("minimumLength")Double minimumLength, @Param("maxLength")Double maxLength, Pageable pageable);
}
