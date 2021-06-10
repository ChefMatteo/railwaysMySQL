package it.uniprisma.railwaysMySQL.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniprisma.railwaysMySQL.persistence.DAO.Ticket;
import it.uniprisma.railwaysMySQL.services.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Add a new ticket")
    @Tag(name = "ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Ticket"))}),
            @ApiResponse(responseCode = "409", description = "Already exists a ticket with id in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createNewTicket(ticket);
    }

    @Operation(summary = "Get tickets list with optional filters")
    @Tag(name = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Ticket> findTicketPage(@RequestParam Integer offset,
                                     @RequestParam Integer limit) {
        return ticketService.findTicketPage(PageRequest.of(offset, limit));
    }

    @Operation(summary = "Get an existing ticket")
    @Tag(name = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Route"))}),
            @ApiResponse(responseCode = "404", description = "No ticket to get", content = @Content)})
    @GetMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public Ticket getSingleTicket(@PathVariable("ticketId") Integer ticketId){
        return ticketService.getSingleTicket(ticketId);
    }

    @Operation(summary = "Update an existing ticket")
    @Tag(name = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Ticket"))}),
            @ApiResponse(responseCode = "404", description = "No ticket to update", content = @Content)})
    @PutMapping("/{ticket_id}/trains/{train_id}")
    @ResponseStatus(HttpStatus.OK)
    public Ticket updateSingleTicket(@PathVariable("ticket_id") Integer ticketId,
                                   @PathVariable("train_id") Integer trainId){
        return ticketService.updateSingleTicket(trainId, ticketId);
    }

    @Operation(summary = "Delete an existing ticket")
    @Tag(name = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No ticket to delete")})
    @DeleteMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTicket(@PathVariable("ticketId") Integer ticketId ){
        ticketService.deleteTicket(ticketId);
    }

}
