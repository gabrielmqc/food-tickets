package com.test.ticket.config;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.application.mappers.TicketMapperBO;
import com.test.ticket.application.useCases.ticket.CreateTicketUseCase;
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
    public CreateTicketUseCase createTicketUseCase(ITicket ticket, TicketMapperBO mapperBO) {
        return  new CreateTicketUseCase(ticket,mapperBO);
    }
}
