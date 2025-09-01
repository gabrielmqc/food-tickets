package com.test.ticket.infrastructure.mappers;

import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.TicketBO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.enums.EntitySituation;
import com.test.ticket.infrastructure.mysql.repositories.EmployeeRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class TicketMapperEntity {

    @Autowired
    EmployeeRepositoryJPA employeRepositoryJPA;

    public TicketDTO toDTO(TicketEntity entity) {
        return new TicketDTO(
                entity.getId(),
                entity.getEmployee().getId(),
                entity.getQuantity(),
                Situation.valueOf(entity.getSituation().name()),
                entity.getAlterationDate()
        );
    }

    public TicketEntity toEntity(TicketDTO dto) {
        Optional<EmployeeEntity> entity = employeRepositoryJPA.findById(dto.employeeId());
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }
        return new TicketEntity(
                dto.id(),
                entity.get(),
                dto.quantity(),
                EntitySituation.valueOf(dto.situation().name()),
                dto.alterationDate()
        );
    }

    public List<TicketDTO> toDTOList(List<TicketEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketEntity> toEntityList(List<TicketDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
