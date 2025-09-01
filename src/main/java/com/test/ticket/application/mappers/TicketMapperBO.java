package com.test.ticket.application.mappers;

import com.test.ticket.application.dtos.request.TicketRequestDTO;
import com.test.ticket.application.dtos.response.TicketResponseDTO;
import com.test.ticket.domain.models.TicketBO;

public class TicketMapperBO {


    public TicketResponseDTO toResponseDTO(TicketBO bo){
        return new TicketResponseDTO(
                bo.getId(),
                bo.getEmployee().getId(),
                bo.getQuantity(),
                bo.getSituation(),
                bo.getAlterationDate()
        );
    }
}
