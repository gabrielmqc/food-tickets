package com.test.ticket.application.contracts;

import java.util.List;
import java.util.UUID;

public interface IGeneralEntity <DTO>{

    DTO create(DTO request);

    DTO update(DTO request, UUID id);

    List<DTO> getAll();

    void save(DTO request);

}
