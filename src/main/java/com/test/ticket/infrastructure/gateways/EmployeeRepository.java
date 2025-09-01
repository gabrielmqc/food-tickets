package com.test.ticket.infrastructure.gateways;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.infrastructure.mappers.EmployeeMapperEntity;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.repositories.EmployeeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class EmployeeRepository implements IEmployee {

    @Autowired
    EmployeeRepositoryJPA repositoryJPA;

    @Autowired
    EmployeeMapperEntity mapperEntity;


    @Override
    public EmployeeDTO create(EmployeeDTO request) {
        EmployeeEntity entity = mapperEntity.toEntity(request);
        return mapperEntity.toResponse(repositoryJPA.save(entity));
    }

    @Override
    public EmployeeDTO update(EmployeeDTO request, UUID id) {
        return null;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<EmployeeEntity> entities =  repositoryJPA.findAll();
        return entities.stream()
                .map(e -> mapperEntity.toResponse(e))
                .toList();
    }

    @Override
    public void save(EmployeeDTO request) {

    }


}
