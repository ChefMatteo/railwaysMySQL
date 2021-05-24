package it.uniprisma.railwaysMySQL.persistence.DAO.Wagon;

import it.uniprisma.railwaysMySQL.models.WagonDOT;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "bed_wagon")
@DiscriminatorValue("b")
public class BedWagon extends Wagon {
    @Column(name = "beds_type")
    private String bedType;
    @Column(name = "beds_available")
    private int nBeds;

    public BedWagon(Train train, WagonDOT w) {
        super(train, w.getWagonType(), w.getWagonClass(), w.getLength(), w.getWidth(), w.getHeight(), w.getBathroom());
        bedType = w.getBedType();
        nBeds = w.getNBeds();
    }
}
