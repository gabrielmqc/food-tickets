package com.test.ticket.application.contracts;

import com.test.ticket.application.dtos.TicketDTO;

import java.util.List;

public interface IReportGenerator {
    byte[] generateTicketsReport(List<TicketDTO> tickets);

}
