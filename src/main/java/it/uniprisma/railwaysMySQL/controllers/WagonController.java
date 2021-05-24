package it.uniprisma.railwaysMySQL.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.uniprisma.railwaysMySQL.models.WagonDOT;
import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Wagon.*;
import it.uniprisma.railwaysMySQL.services.WagonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wagons")
public class WagonController {

    private final WagonService wagonService;

    public WagonController(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Operation(summary = "Add a new wagon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(oneOf = {RestaurantWagon.class, PassengerWagon.class, BedWagon.class, MotorWagon.class}))}),
            @ApiResponse(responseCode = "409", description = "Already exists a train with id in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Wagon createTrain(@RequestBody WagonDOT wagonDOT) {
        return wagonService.createSingleWagon(wagonDOT);
    }

    @Operation(summary = "Get wagons list with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Wagon> findWagonPage(@RequestParam(required = false) Boolean bathroom,
                                     @RequestParam(required = false) WagonClass wagonClass,
                                     @RequestParam(required = false) WagonType wagonType,
                                     @RequestParam(required = false) Integer minimumTables,
                                     @RequestParam(required = false) String kitchenType,
                                     @RequestParam(required = false) FuelType fuelType,
                                     @RequestParam(required = false) Integer minimumBeds,
                                     @RequestParam(required = false) String bedType,
                                     @RequestParam(required = false) Integer minimumSeats,
                                     @RequestParam Integer offset,
                                     @RequestParam Integer limit) {
        return wagonService.findWagonPage(bathroom, wagonClass, wagonType, minimumTables, kitchenType, fuelType,
                                     minimumBeds, bedType, minimumSeats, PageRequest.of(offset, limit));
    }

    @Operation(summary = "Get an existing wagon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", oneOf = {RestaurantWagon.class, PassengerWagon.class, BedWagon.class, MotorWagon.class}))}),
            @ApiResponse(responseCode = "404", description = "No wagon to get", content = @Content)})
    @GetMapping("/{wagonId}")
    @ResponseStatus(HttpStatus.OK)
    public Wagon getSingleWagon(@PathVariable("wagonId") Integer wagonId ){
        return wagonService.getSingleWagon(wagonId);
    }

    @Operation(summary = "Update an existing wagon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wagon successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", oneOf = {RestaurantWagon.class, PassengerWagon.class, BedWagon.class, MotorWagon.class}))}),
            @ApiResponse(responseCode = "400", description = "Parameters not available for type of wagon in body", content = @Content),
            @ApiResponse(responseCode = "404", description = "No wagon to update", content = @Content)})
    @PutMapping("/{wagonId}")
    @ResponseStatus(HttpStatus.OK)
    public Wagon updateSingleWagon(@PathVariable("wagonId") Integer wagonId,
                                   @RequestBody WagonDOT wagonDOT){
        return wagonService.updateSingleWagon(wagonDOT, wagonId);
    }

    @Operation(summary = "Delete an existing wagon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No wagon to delete")})
    @DeleteMapping("/{wagonId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWagon(@PathVariable("wagonId") int wagonId ){
        wagonService.deleteSingleWagon(wagonId);
    }

}