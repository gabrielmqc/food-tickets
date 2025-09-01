package com.test.ticket.application.contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGeneralEntity <DTO>{

    DTO create(DTO request);

    Optional<DTO> getById(UUID id);

    Optional<DTO> update(DTO request, UUID id);

    List<DTO> getAll();

    List<DTO> getByIds(List<UUID> ids);

    void save(DTO request);

}
