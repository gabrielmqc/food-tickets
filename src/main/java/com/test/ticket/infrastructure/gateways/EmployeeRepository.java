package com.test.ticket.infrastructure.gateways;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.infrastructure.mappers.EmployeeMapperEntity;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.repositories.EmployeeRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
        return mapperEntity.toDTO(repositoryJPA.save(entity));
    }

    @Override
    public Optional<EmployeeDTO> getById(UUID id) {
        Optional<EmployeeEntity> entity = repositoryJPA.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Ticket not found");
        }
        return entity.map(mapperEntity::toDTO);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO request, UUID id) {
        return null;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<EmployeeEntity> entities = repositoryJPA.findAll();
        return mapperEntity.toDTOList(entities);
    }

    @Override
    public List<EmployeeDTO> getByIds(List<UUID> ids) {
        List<EmployeeEntity> employeeEntities = repositoryJPA.findAllById(ids);
        return mapperEntity.toDTOList(employeeEntities);
    }

    @Override
    public void save(EmployeeDTO request) {

    }


}
