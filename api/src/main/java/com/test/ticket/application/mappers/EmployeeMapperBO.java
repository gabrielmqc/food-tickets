package com.test.ticket.application.mappers;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;

import java.util.List;
import java.util.stream.Collectors;


public class EmployeeMapperBO {

    private final ITicket ticketRepository;
    private final TicketMapperBO mapperBO;

    public EmployeeMapperBO(ITicket ticketRepository, TicketMapperBO mapperBO) {
        this.ticketRepository = ticketRepository;
        this.mapperBO = mapperBO;
    }

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

    public  EmployeeBO toBO(EmployeeDTO dto) {
        List<TicketDTO> ticketDTOS = ticketRepository.getByIds(dto.ticketsIds());
        return new EmployeeBO(
                dto.id(),
                dto.name(),
                dto.cpf(),
                dto.situation(),
                dto.alterationDate(),
                mapperBO.toBOList(ticketDTOS)

        );
    }

    public List<EmployeeDTO> toDTOList(List<EmployeeBO> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeBO> toBOList(List<EmployeeDTO> dtos) {
        return dtos.stream()
                .map(this::toBO)
                .collect(Collectors.toList());
    }

}
