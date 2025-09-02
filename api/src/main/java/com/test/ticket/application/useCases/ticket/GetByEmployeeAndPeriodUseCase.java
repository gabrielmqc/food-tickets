package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GetByEmployeeAndPeriodUseCase {

    private final ITicket repository;


    public GetByEmployeeAndPeriodUseCase(ITicket repository) {
        this.repository = repository;
    }

    public List<TicketDTO> invoke(UUID employeeId, LocalDateTime startDate, LocalDateTime endDate) {

        return repository.getByEmployeeAndPeriod(employeeId,startDate,endDate);
    }
}
