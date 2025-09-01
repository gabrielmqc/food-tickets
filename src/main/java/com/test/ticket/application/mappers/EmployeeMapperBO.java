package com.test.ticket.application.mappers;

import com.test.ticket.application.dtos.request.EmployeeRequestDTO;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.domain.models.EmployeeBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface  EmployeeMapperBO{
    EmployeeMapperBO INSTANCE = Mappers.getMapper(EmployeeMapperBO.class);

    EmployeeBO toBO(EmployeeRequestDTO dto);

    EmployeeResponseDTO toResponseDTO(EmployeeBO bo);
}
