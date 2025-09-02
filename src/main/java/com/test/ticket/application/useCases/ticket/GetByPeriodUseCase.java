package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;

public class GetByPeriodUseCase {

    private final ITicket repository;


    public GetByPeriodUseCase(ITicket repository) {
        this.repository = repository;
    }

    public List<TicketDTO> invoke(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getByPeriod(startDate,endDate);
    }
}
