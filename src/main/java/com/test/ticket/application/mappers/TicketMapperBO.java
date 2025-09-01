package com.test.ticket.application.mappers;

import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.domain.models.TicketBO;

public class TicketMapperBO {


    public TicketDTO toResponseDTO(TicketBO bo){
        return new TicketDTO(
                bo.getId(),
                bo.getEmployee().getId(),
                bo.getQuantity(),
                bo.getSituation(),
                bo.getAlterationDate()
        );
    }
}
