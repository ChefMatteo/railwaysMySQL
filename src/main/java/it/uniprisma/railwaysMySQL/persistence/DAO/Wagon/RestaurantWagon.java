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
@Entity(name = "restaurant_wagon")
@DiscriminatorValue("r")
public class RestaurantWagon extends Wagon {
    @Column(name = "kitchen_type")
    private String kitchenType;
    @Column(name = "kitchen_tables")
    private int nTables;

    public RestaurantWagon(Train train, WagonDOT w) {
        super(train, w.getWagonType(), w.getWagonClass(), w.getLength(), w.getWidth(), w.getHeight(), w.getBathroom());
        kitchenType = w.getKitchenType();
        nTables = w.getNTables();
    }

}
