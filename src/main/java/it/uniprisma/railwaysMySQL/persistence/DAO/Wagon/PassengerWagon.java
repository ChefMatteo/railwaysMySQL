package it.uniprisma.railwaysMySQL.persistence.DAO.Wagon;

import it.uniprisma.railwaysMySQL.models.WagonDOT;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity(name = "passenger_wagon")
@DiscriminatorValue("p")
public class PassengerWagon extends Wagon {
    @Column(name = "passenger_seats")
    private int nSeats;

    public PassengerWagon(Train train, WagonDOT w) {
        super(train, w.getWagonType(), w.getWagonClass(), w.getLength(), w.getWidth(), w.getHeight(), w.getBathroom());
        nSeats = w.getNSeats();
    }

}
