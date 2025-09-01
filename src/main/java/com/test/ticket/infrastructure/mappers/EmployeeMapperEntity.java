package com.test.ticket.infrastructure.mappers;

import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.enums.EntitySituation;
import com.test.ticket.infrastructure.mysql.repositories.TicketRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class EmployeeMapperEntity {

    @Autowired
    TicketRepositoryJPA ticketRepositoryJPA;

    public EmployeeEntity toEntity(EmployeeDTO dto) {

        List<TicketEntity> ticketEntities = dto.ticketsIds().stream().map(id -> ticketRepositoryJPA.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket not found")))
                .toList();

        return new EmployeeEntity(
                dto.id(),
                dto.name(),
                dto.cpf(),
                EntitySituation.valueOf(dto.situation().name()),
                dto.alterationDate(),
                List.of()
        );
    }

    public EmployeeDTO toResponse(EmployeeEntity entity) {

        return new EmployeeDTO(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                Situation.valueOf(entity.getSituation().name()),
                entity.getAlterationDate(),
                entity.getTickets().stream().map(TicketEntity::getId).toList()
        );
    }


}
