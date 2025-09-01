package com.test.ticket.config;

import com.test.ticket.application.mappers.TicketMapperBO;
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
    public TicketMapperBO ticketMapperBO() {
        return new TicketMapperBO();
    }
}
