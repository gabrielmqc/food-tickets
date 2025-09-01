package com.test.ticket.application.mappers;

import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;


public class EmployeeMapperBO {


    public EmployeeDTO toDTO(EmployeeBO bo) {
        return new EmployeeDTO(
                bo.getId(),
                bo.getName(),
                bo.getCpf(),
                bo.getSituation(),
                bo.getAlterationDate(),
                bo.getTickets().stream().map(TicketBO::getId).toList()
        );
    }

}
