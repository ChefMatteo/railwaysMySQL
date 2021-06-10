package it.uniprisma.railwaysMySQL.persistence.repositories;

import it.uniprisma.railwaysMySQL.persistence.DAO.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
    Page<Ticket> findAll(Pageable pageable);
}
