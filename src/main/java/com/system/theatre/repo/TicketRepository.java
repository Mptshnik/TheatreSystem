package com.system.theatre.repo;

import com.system.theatre.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
