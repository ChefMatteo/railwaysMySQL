package it.uniprisma.railwaysMySQL.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WagonDOT {
    private int id;
    private int trainId;
    private WagonClass wagonClass;
    private WagonType wagonType;
    private double length;
    private double width;
    private double height;
    private Boolean bathroom;
    private FuelType fuelType;
    private int nSeats;
    private String kitchenType;
    private int nTables;
    private String bedType;
    private int nBeds;
}
