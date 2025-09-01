package com.test.ticket.application.mappers;

import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.application.dtos.response.TicketResponseDTO;
import com.test.ticket.domain.models.EmployeeBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class EmployeeMapperBO {

    @Autowired
    TicketMapperBO mapperBO;

    EmployeeResponseDTO toResponseDTO(EmployeeBO bo) {
        List<TicketResponseDTO> ticketResponseDTOS = bo.getTickets().stream().map(mapperBO::toResponseDTO).toList();
        return new EmployeeResponseDTO(
                bo.getId(),
                bo.getName(),
                bo.getCpf(),
                bo.getSituation(),
                bo.getAlterationDate(),
                ticketResponseDTOS);
    }
}
