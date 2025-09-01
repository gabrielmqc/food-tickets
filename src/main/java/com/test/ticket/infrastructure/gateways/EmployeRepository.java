package com.test.ticket.infrastructure.gateways;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.response.EmployeeResponseDTO;
import com.test.ticket.domain.models.EmployeeBO;
import com.test.ticket.infrastructure.mappers.EmployeeMapperEntity;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.repositories.EmployeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class EmployeRepository implements IEmployee {

    @Autowired
    EmployeRepositoryJPA repositoryJPA;

    @Autowired
    EmployeeMapperEntity mapperEntity;

    @Override
    public EmployeeResponseDTO create(EmployeeBO request) {
        EmployeeEntity entity = mapperEntity.toEntity(request);
        return mapperEntity.toReponse(repositoryJPA.save(entity));
    }

    @Override
    public EmployeeResponseDTO update(EmployeeBO request, UUID id) {
        return null;
    }

    @Override
    public List<EmployeeResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public void save(EmployeeBO request) {

    }
}
