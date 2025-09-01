package com.test.ticket.application.contracts;

import java.util.List;
import java.util.UUID;

public interface IGeneralEntity <ResponseDTO, BO>{

    ResponseDTO create(BO request);

    ResponseDTO update(BO request, UUID id);

    List<ResponseDTO> getAll();

    void save(BO request);

}
