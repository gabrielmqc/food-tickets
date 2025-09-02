package com.test.ticket.application.contracts;

import com.test.ticket.application.dtos.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ITicket extends IGeneralEntity<TicketDTO>{

    List<TicketDTO> getByEmployeeId (UUID employeeId);
    List<TicketDTO> getByEmployeeAndPeriod(UUID employeeId, LocalDateTime startDate, LocalDateTime endDate);
    List<TicketDTO> getByPeriod( LocalDateTime startDate, LocalDateTime endDate);

}
