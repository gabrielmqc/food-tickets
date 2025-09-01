package com.test.ticket.config;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.application.useCases.ticket.CreateTicketUseCase;
import com.test.ticket.application.useCases.ticket.GetAllTicketsUseCase;
import com.test.ticket.application.useCases.ticket.UpdateTicketUseCase;
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
        return new UpdateTicketUseCase(ticket,ticketMapperBO);
    }

    @Bean
    public GetAllTicketsUseCase getAllTicketsUseCase(ITicket ticket) {
        return new GetAllTicketsUseCase(ticket);
    }

    @Bean
    public CreateTicketUseCase createTicketUseCase(ITicket ticket, TicketMapperBO mapperBO, IEmployee employee) {
        return  new CreateTicketUseCase(ticket,mapperBO,employee);
    }
}
