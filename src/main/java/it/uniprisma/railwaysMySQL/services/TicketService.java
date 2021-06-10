package it.uniprisma.railwaysMySQL.services;

import it.uniprisma.railwaysMySQL.persistence.DAO.Ticket;
import it.uniprisma.railwaysMySQL.persistence.DAO.Train;
import it.uniprisma.railwaysMySQL.persistence.repositories.TicketRepo;
import it.uniprisma.railwaysMySQL.persistence.repositories.TrainRepo;
import it.uniprisma.railwaysMySQL.utils.ConflictException;
import it.uniprisma.railwaysMySQL.utils.NotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;
    private final TrainRepo trainRepo;
    private final RabbitTemplate rabbitTemplate;

    public TicketService(TicketRepo ticketRepo, RabbitTemplate rabbitTemplate, TrainRepo trainRepo) {
        this.ticketRepo = ticketRepo;
        this.trainRepo = trainRepo;
        this.rabbitTemplate = rabbitTemplate;
    }


    public Ticket createNewTicket(Ticket ticket) {
        Ticket ticketToReturn = ticketRepo.save(ticket);
        rabbitTemplate.convertAndSend("railways_exchange", "create", "TicketID: "+ticket.getId());
        return ticketToReturn;
    }

    public Page<Ticket> findTicketPage(Pageable pageable) {
        return ticketRepo.findAll(pageable);
    }

    public Ticket getSingleTicket(Integer ticketId) {
        return ticketRepo.findById(ticketId).orElseThrow(()-> new NotFoundException("Ticket", ticketId));
    }

    public Ticket updateSingleTicket(Integer trainId, Integer ticketId) {
        Ticket ticketToUpdate = ticketRepo.findById(ticketId).orElseThrow(()->new NotFoundException("Ticket", ticketId));
        Train trainToAdd = trainRepo.findById(trainId).orElseThrow(() -> new NotFoundException("Train", trainId));
        if(trainToAdd!=ticketToUpdate.getTrain()){
            return ticketRepo.save(ticketToUpdate);
        }
        throw new ConflictException("Ticket have already that train");
    }


    public void deleteTicket(Integer ticketId) {
        try {
            ticketRepo.deleteById(ticketId);
        }catch (Exception e){
            throw new NotFoundException("Ticket", ticketId);
        }
        rabbitTemplate.convertAndSend("railways_exchange", "delete", "TicketID: "+ticketId);
    }

}
