package it.uniprisma.railwaysMySQL.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniprisma.railwaysMySQL.models.enums.FuelType;
import it.uniprisma.railwaysMySQL.models.enums.TrainType;
import it.uniprisma.railwaysMySQL.models.enums.WagonClass;
import it.uniprisma.railwaysMySQL.models.enums.WagonType;
import it.uniprisma.railwaysMySQL.persistence.DAO.Route;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import it.uniprisma.railwaysMySQL.persistence.DAO.Wagon.Wagon;
import it.uniprisma.railwaysMySQL.services.TrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @Operation(summary = "Add a new train")
    @Tag(name = "trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Train"))}),
            @ApiResponse(responseCode = "409", description = "Already exists a train with id in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Train createTrain(@RequestBody Train train) {
        return trainService.createNewTrain(train);
    }

    @Operation(summary = "Get trains list with optional filters")
    @Tag(name = "trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Train> findTrainPage(@RequestParam(required = false) String companyName,
                                     @RequestParam(required = false) TrainType trainType,
                                     @RequestParam Integer offset,
                                     @RequestParam Integer limit) {
        return trainService.findTrains(companyName, trainType, PageRequest.of(offset, limit));
    }

    @Operation(summary = "Get an existing train")
    @Tag(name = "trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Train"))}),
            @ApiResponse(responseCode = "404", description = "No train to get", content = @Content)})
    @GetMapping("/{trainId}")
    @ResponseStatus(HttpStatus.OK)
    public Train getSingleTrain(@PathVariable("trainId") Integer trainId ){
        return trainService.getSingleTrain(trainId);
    }

    @Operation(summary = "Update an existing train")
    @Tag(name = "trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Train"))}),
            @ApiResponse(responseCode = "404", description = "No train to update", content = @Content)})
    @PutMapping("/{trainId}")
    @ResponseStatus(HttpStatus.OK)
    public Train updateSingleTrain(@PathVariable("trainId") Integer trainId,
                                   @RequestBody Train train){
        return trainService.updateSingleTrain(train, trainId);
    }

    @Operation(summary = "Delete an existing train")
    @Tag(name = "trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No train to delete")})
    @DeleteMapping("/{trainId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrain(@PathVariable("trainId") Integer trainId ){
        trainService.deleteTrain(trainId);
    }

    @Operation(summary = "Get wagons list of a specific train with optional filters")
    @Tag(name = "wagons-trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "404", description = "No train to get", content = @Content)})
    @GetMapping("/{trainId}/wagons")
    @ResponseStatus(HttpStatus.OK)
    public Page<Wagon> findWagonsOfTrainPage(@PathVariable("trainId") int trainId,
                                             @RequestParam(required = false) WagonType wagonType,
                                             @RequestParam(required = false) String kitchenType,
                                             @RequestParam(required = false) FuelType fuelType,
                                             @RequestParam(required = false) Boolean bathroom,
                                             @RequestParam(required = false) WagonClass wagonClass,
                                             @RequestParam(required = false) String bedType,
                                             @RequestParam(required = false) Integer minimumTables,
                                             @RequestParam(required = false) Integer minimumBeds,
                                             @RequestParam(required = false) Integer minimumSeats,
                                             @RequestParam Integer offset,
                                             @RequestParam Integer limit) {
        return trainService.findWagonsOfTrainPage(trainId, wagonType, kitchenType, fuelType,
                                                  bathroom, wagonClass, bedType, minimumBeds, minimumTables, minimumSeats, PageRequest.of(offset, limit));
    }

    @Operation(summary = "Get routes list of a specific train with optional filters")
    @Tag(name = "routes-trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "404", description = "No train to get", content = @Content)})
    @GetMapping("/{trainId}/routes")
    @ResponseStatus(HttpStatus.OK)
    public Page<Route> findRoutesOfTrainPage(@PathVariable("trainId") Integer trainId,
                                             @RequestParam(required = false) String startStation,
                                             @RequestParam(required = false) String destinationStation,
                                             @RequestParam(required = false) String binaryType,
                                             @RequestParam(required = false) Double minimumLength,
                                             @RequestParam(required = false) Double maxLength,
                                             @RequestParam Integer offset,
                                             @RequestParam Integer limit) {
        return trainService.findRoutesOfTrainPage(trainId, startStation, destinationStation, binaryType, minimumLength, maxLength, PageRequest.of(offset, limit));
    }

    @Operation(summary = "Add a new association between route and train")
    @Tag(name = "routes-trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content}),
            @ApiResponse(responseCode = "409", description = "Already exists an association between ids in path", content = @Content)})
    @PostMapping("/{trainId}/routes/{routeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrainRouteAssociation(@PathVariable("trainId") Integer trainId, @PathVariable("routeId") Integer routeId) {
        trainService.createTrainRouteAssociation(trainId, routeId);
    }

    @Operation(summary = "Delete an existing association between train and route")
    @Tag(name = "routes-trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No association to delete")})
    @DeleteMapping("/{trainId}/routes/{routeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainRouteAssociation(@PathVariable("trainId") Integer trainId, @PathVariable("routeId") Integer routeId) {
         trainService.deleteTrainRouteAssociation(trainId, routeId);
    }

    @Operation(summary = "Add or rewrite a new association between wagon and train")
    @Tag(name = "wagons-trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content}),
            @ApiResponse(responseCode = "409", description = "Already exists an association between ids in path", content = @Content)})
    @PostMapping("/{trainId}/wagons/{wagonId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdateTrainWagonAssociation(@PathVariable("trainId") Integer trainId, @PathVariable("wagonId") Integer wagonId) {
         trainService.createOrUpdateTrainWagonAssociation(trainId, wagonId);
    }



}