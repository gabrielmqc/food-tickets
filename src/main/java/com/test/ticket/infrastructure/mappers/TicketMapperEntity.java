package com.test.ticket.infrastructure.mappers;

import com.test.ticket.application.dtos.response.TicketResponseDTO;
import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.domain.models.TicketBO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.enums.EntitySituation;
import org.springframework.beans.factory.annotation.Autowired;


public class TicketMapperEntity {

    public TicketBO toBO(TicketEntity entity, EmployeeMapperEntity employeeMapper) {
        EmployeeBO employeeBO = employeeMapper.toBO(entity.getEmployee(), this);
        return new TicketBO(
                entity.getId(),
                employeeBO,
                entity.getQuantity(),
                Situation.valueOf(entity.getSituation().name()),
                entity.getAlterationDate()
        );
    }

    public TicketEntity toEntity(TicketBO bo, EmployeeMapperEntity employeeMapper) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(bo.getEmployee(), this);
        return new TicketEntity(
                bo.getId(),
                employeeEntity,
                bo.getQuantity(),
                EntitySituation.valueOf(bo.getSituation().name()),
                bo.getAlterationDate()
        );
    }

    public TicketResponseDTO toResponse(TicketEntity entity) {
        return new TicketResponseDTO(
                entity.getId(),
                entity.getEmployee().getId(), // só o ID do funcionário
                entity.getQuantity(),
                Situation.valueOf(entity.getSituation().name()),
                entity.getAlterationDate()
        );
    }

}
