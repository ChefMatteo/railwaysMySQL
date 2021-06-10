package it.uniprisma.railwaysMySQL.persistence.DAO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Schema(name = "Ticket")
@Data
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;

}
