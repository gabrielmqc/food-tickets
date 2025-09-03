package com.test.ticket.infrastructure.gateways;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.infrastructure.mappers.EmployeeMapperEntity;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.repositories.EmployeeRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
            throw new EntityNotFoundException("Employee not found");
        }
        return entity.map(mapperEntity::toDTO);
    }

    @Override
    public Optional<EmployeeDTO> update(EmployeeDTO request, UUID id) {
        return repositoryJPA.findById(id)
                .map(existingEmployee -> {
                    if (request.name() != null) {
                        existingEmployee.setName(request.name());
                    }
                    if (request.cpf() != null) {
                        existingEmployee.setCpf(request.cpf());
                    }
                    existingEmployee.setAlterationDate(request.alterationDate());
                    EmployeeEntity savedEntity = repositoryJPA.save(existingEmployee);
                    return mapperEntity.toDTO(savedEntity);
                });
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
    public void save(EmployeeDTO employeeDTO) {
        EmployeeEntity entity = mapperEntity.toEntity(employeeDTO);
        repositoryJPA.save(entity);
    }


}
