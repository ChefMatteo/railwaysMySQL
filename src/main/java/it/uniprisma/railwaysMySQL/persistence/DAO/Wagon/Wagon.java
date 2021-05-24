package it.uniprisma.railwaysMySQL.persistence.DAO.Wagon;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.uniprisma.railwaysMySQL.models.WagonDOT;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import it.uniprisma.railwaysMySQL.utils.BadRequestException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "wagon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    protected Train train;
    @Column
    @Enumerated(EnumType.STRING)
    protected WagonClass wagonClass;
    protected double length;
    protected double width;
    protected double height;
    protected Boolean bathroom;
    protected WagonType wagonType;

    public Wagon(Train train, WagonType wagonType, WagonClass wagonClass, double length, double width,
                 double height, Boolean bathroom) {
        this.wagonClass = wagonClass;
        this.length = length;
        this.width = width;
        this.height = height;
        this.bathroom = bathroom;
        this.wagonType = wagonType;
        if(train!=null){
            this.train = train;
        }
    }

    public static Wagon createWagon(Train train, WagonDOT wagonDOT){
        try {
            switch (Objects.requireNonNull(wagonDOT.getWagonType())) {
                case RESTAURANT -> {
                    return new RestaurantWagon(train, wagonDOT);
                }
                case PASSENGER -> {
                    return new PassengerWagon(train, wagonDOT);
                }
                case MOTOR -> {
                    return new MotorWagon(train, wagonDOT);
                }
                case BED -> {
                    return new BedWagon(train, wagonDOT);
                }
                default -> throw new BadRequestException("Bad request");

            }
        } catch (Exception e) {
            throw new BadRequestException("Wagon type can't be null");
        }
    }

}
