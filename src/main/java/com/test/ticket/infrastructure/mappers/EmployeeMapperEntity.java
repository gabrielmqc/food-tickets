package com.test.ticket.infrastructure.mappers;

import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapperEntity {
    EmployeeMapperEntity INSTANCE = Mappers.getMapper(EmployeeMapperEntity.class);

    EmployeeBO toBO(EmployeeEntity entity);

    EmployeeEntity toEntity(EmployeeBO bo);

    EmployeeResponseDTO toReponse(EmployeeEntity entity);
}
