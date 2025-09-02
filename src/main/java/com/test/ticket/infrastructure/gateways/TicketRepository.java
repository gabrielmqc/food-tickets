package com.test.ticket.infrastructure.gateways;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.infrastructure.mappers.TicketMapperEntity;
import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.repositories.EmployeeRepositoryJPA;
import com.test.ticket.infrastructure.mysql.repositories.TicketRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TicketRepository implements ITicket {

    @Autowired
    TicketRepositoryJPA repositoryJPA;

    @Autowired
    TicketMapperEntity mapperEntity;

    @Autowired
    EmployeeRepositoryJPA employeeRepositoryJPA;

    @Override
    public TicketDTO create(TicketDTO request) {
        TicketEntity entity = mapperEntity.toEntity(request);
        return mapperEntity.toDTO(repositoryJPA.save(entity));
    }

    @Override
    public Optional<TicketDTO> getById(UUID id) {
        Optional<TicketEntity> entity = repositoryJPA.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Ticket not found");
        }
        return entity.map(mapperEntity::toDTO);
    }

    @Override
    public Optional<TicketDTO> update(TicketDTO request, UUID id) {
        System.out.println(request.toString());
        return repositoryJPA.findById(id)
                .map(existingTicket -> {
                    if (request.employeeId() != null) {
                        Optional<EmployeeEntity> employee = employeeRepositoryJPA.findById(request.employeeId());
                        if (employee.isEmpty()) {
                            throw new EntityNotFoundException("New employee not found");
                        }
                        existingTicket.setEmployee(employee.get());
                    }
                    if (request.quantity() != null) {
                        existingTicket.setQuantity(request.quantity());
                    }
                    existingTicket.setAlterationDate(request.alterationDate());
                    TicketEntity ticketEntity = repositoryJPA.save(existingTicket);
                    return mapperEntity.toDTO(ticketEntity);
                });
    }

    @Override
    public List<TicketDTO> getAll() {
        return mapperEntity.toDTOList(repositoryJPA.findAll());
    }

    @Override
    public List<TicketDTO> getByIds(List<UUID> ids) {

        return mapperEntity.toDTOList(repositoryJPA.findAllById(ids));
    }

    @Override
    public void save(TicketDTO ticketDTO) {
        TicketEntity entity = mapperEntity.toEntity(ticketDTO);
        repositoryJPA.save(entity);
    }

    @Override
    public List<TicketDTO> getByEmployeeId(UUID employeeId) {
        return mapperEntity.toDTOList(repositoryJPA.findByEmployeeId(employeeId));
    }

    @Override
    public List<TicketDTO> getByEmployeeAndPeriod(UUID employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        return mapperEntity.toDTOList(repositoryJPA.findByEmployeeAndPeriod(employeeId,startDate,endDate));
    }

    @Override
    public List<TicketDTO> getByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return mapperEntity.toDTOList(repositoryJPA.findByPeriod(startDate,endDate));
    }
}
