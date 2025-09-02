package com.test.ticket.application.useCases.ticket;

import com.test.ticket.application.contracts.IReportGenerator;
import com.test.ticket.application.dtos.TicketDTO;

import java.util.List;

public class GenerateTicketReportPdfUseCase {
    private final IReportGenerator reportGenerator;


    public GenerateTicketReportPdfUseCase(IReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    public byte[] invoke(List<TicketDTO> ticketDTOS) {
        return reportGenerator.generateTicketsReport(ticketDTOS);
    }
}
