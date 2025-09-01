package com.test.ticket.infrastructure.gateways;

import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.TicketDTO;
import com.test.ticket.infrastructure.mappers.TicketMapperEntity;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import com.test.ticket.infrastructure.mysql.repositories.TicketRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TicketRepository implements ITicket {

    @Autowired
    TicketRepositoryJPA repositoryJPA;

    @Autowired
    TicketMapperEntity mapperEntity;

    @Override
    public TicketDTO create(TicketDTO request) {
        TicketEntity entity = mapperEntity.toEntity(request);
        return mapperEntity.toDTO(repositoryJPA.save(entity));
    }

    @Override
    public Optional<TicketDTO> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public TicketDTO update(TicketDTO request, UUID id) {
        return null;
    }

    @Override
    public List<TicketDTO> getAll() {
        return List.of();
    }

    @Override
    public List<TicketDTO> getByIds(List<UUID> ids) {
        return List.of();
    }

    @Override
    public void save(TicketDTO request) {

    }
}
