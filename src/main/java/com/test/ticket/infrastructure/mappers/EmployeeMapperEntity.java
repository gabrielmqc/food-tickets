package com.test.ticket.infrastructure.mappers;

import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.application.dtos.response.TicketResponseDTO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.enums.EntitySituation;

import java.util.List;


public class EmployeeMapperEntity {

    public EmployeeBO toBO(EmployeeEntity entity, TicketMapperEntity ticketMapper) {
        List<TicketBO> ticketBOS = entity.getTickets().stream()
                .map(t -> ticketMapper.toBO(t, this)) // passa this como mapper para Ticket
                .toList();
        return new EmployeeBO(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                Situation.valueOf(entity.getSituation().name()),
                entity.getAlterationDate(),
                ticketBOS
        );
    }

    public EmployeeEntity toEntity(EmployeeBO bo, TicketMapperEntity ticketMapper) {
        List<TicketEntity> ticketEntities = bo.getTickets().stream()
                .map(t -> ticketMapper.toEntity(t, this))
                .toList();

        return new EmployeeEntity(
                bo.getId(),
                bo.getName(),
                bo.getCpf(),
                EntitySituation.valueOf(bo.getSituation().name()),
                bo.getAlterationDate(),
                ticketEntities
        );
    }

    public EmployeeResponseDTO toResponse(EmployeeEntity entity, TicketMapperEntity ticketMapper) {
        List<TicketResponseDTO> ticketResponseDTOS = entity.getTickets().stream()
                .map(t -> ticketMapper.toResponse(t)) // TicketMapperEntity não precisa do EmployeeMapper aqui
                .toList();

        return new EmployeeResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                Situation.valueOf(entity.getSituation().name()),
                entity.getAlterationDate(),
                ticketResponseDTOS
        );
    }


}
