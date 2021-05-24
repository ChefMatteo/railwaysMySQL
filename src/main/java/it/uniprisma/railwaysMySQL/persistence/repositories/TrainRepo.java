package it.uniprisma.railwaysMySQL.persistence.repositories;


import it.uniprisma.railwaysMySQL.models.enums.TrainType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainRepo extends JpaRepository<Train, Integer> {
    @Query("select t from Train t where " +
            "((:company is null or t.company = :company) " +
            "and (:trainType is null or t.type = :trainType))")
    Page<Train> findAllWithFilters(@Param("company") String company, @Param("trainType") TrainType trainType, Pageable pageable);

}
