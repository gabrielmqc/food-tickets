package com.test.ticket.config;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.application.useCases.ticket.*;
import com.test.ticket.infrastructure.mappers.TicketMapperEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {

    @Bean
    public TicketMapperEntity ticketMapperEntity() {
        return new TicketMapperEntity();
    }

    @Bean
    public TicketMapperBO ticketMapperBO(IEmployee employee) {
        return new TicketMapperBO(employee);
    }

    @Bean
    public UpdateTicketUseCase updateTicketUseCase(ITicket ticket, TicketMapperBO ticketMapperBO) {
        return new UpdateTicketUseCase(ticket, ticketMapperBO);
    }

    @Bean
    public GetByEmployeeAndPeriodUseCase getByEmployeeAndPeriodUseCase(ITicket ticket) {
        return new GetByEmployeeAndPeriodUseCase(ticket);
    }

    @Bean
    public GetByPeriodUseCase getByPeriodUseCase (ITicket ticket) {
        return new GetByPeriodUseCase(ticket);
    }

    @Bean
    public GetByEmployeeUseCase getByEmployeeUseCase (ITicket ticket) {
        return new GetByEmployeeUseCase(ticket);
    }

    @Bean
    public ActivateTicketUseCase activateTicketUseCase(ITicket ticket, TicketMapperBO ticketMapperBO) {
        return new ActivateTicketUseCase(ticket, ticketMapperBO);
    }

    @Bean
    public DeactivateTicketUseCase deactivateTicketUseCase(ITicket ticket, TicketMapperBO ticketMapperBO) {
        return new DeactivateTicketUseCase(ticket, ticketMapperBO);
    }

    @Bean
    public GetAllTicketsUseCase getAllTicketsUseCase(ITicket ticket) {
        return new GetAllTicketsUseCase(ticket);
    }

    @Bean
    public CreateTicketUseCase createTicketUseCase(ITicket ticket, TicketMapperBO mapperBO, IEmployee employee) {
        return new CreateTicketUseCase(ticket, mapperBO, employee);
    }
}
