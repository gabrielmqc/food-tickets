package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.interfaces.Creation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EmployeeBO extends AbstractModel implements Creation{

    private String name;
    private String cpf;
    private List<TicketBO> tickets;

    public EmployeeBO(UUID id, String name, String cpf, Situation situation, LocalDateTime alterationDate, List<TicketBO> tickets ) {
        super(id, situation, alterationDate);
        this.name = name;
        this.cpf = cpf;
        this.tickets = tickets;
    }


}
