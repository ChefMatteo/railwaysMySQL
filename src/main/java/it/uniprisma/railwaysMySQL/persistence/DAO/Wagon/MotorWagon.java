package it.uniprisma.railwaysMySQL.persistence.DAO.Wagon;

import it.uniprisma.railwaysMySQL.models.WagonDOT;
import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity(name = "motor_wagon")
@DiscriminatorValue("m")
public class MotorWagon extends Wagon {
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    public MotorWagon(Train train, WagonDOT w) {
        super(train, w.getWagonType(), w.getWagonClass(), w.getLength(), w.getWidth(), w.getHeight(), w.getBathroom());
        fuelType = w.getFuelType();
    }

}
