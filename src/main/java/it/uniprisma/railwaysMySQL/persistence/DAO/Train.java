package it.uniprisma.railwaysMySQL.persistence.DAO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import it.uniprisma.railwaysMySQL.models.enums.TrainType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Wagon.Wagon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Schema(name = "Train")
@Data
@NoArgsConstructor
@Entity
@Table(name = "train")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Train.class)
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    @Enumerated(EnumType.STRING)
    private TrainType type;
    private String company;
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JoinTable(name = "route_trains",
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    private Set<Route> routes = new HashSet<>();
    @EqualsAndHashCode.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Wagon> wagons = new HashSet<>();
    @EqualsAndHashCode.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Ticket> tickets = new HashSet<>();


}
