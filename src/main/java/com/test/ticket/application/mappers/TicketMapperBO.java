package com.test.ticket.application.mappers;

import com.test.ticket.application.dtos.request.TicketRequestDTO;
import com.test.ticket.application.dtos.response.TicketResponseDTO;
import com.test.ticket.domain.models.TicketBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapperBO {
    TicketMapperBO INSTANCE = Mappers.getMapper(TicketMapperBO.class);

    TicketBO toBO(TicketRequestDTO dto) ;

    TicketResponseDTO toResponseDTO(TicketBO bo);
}
