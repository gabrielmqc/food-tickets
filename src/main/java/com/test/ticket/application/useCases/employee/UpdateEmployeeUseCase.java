package com.test.ticket.application.useCases.employee;

import com.test.ticket.application.contracts.IEmployee;
import com.test.ticket.application.contracts.ITicket;
import com.test.ticket.application.dtos.EmployeeDTO;
import com.test.ticket.application.exceptions.NotFoundException;
import com.test.ticket.application.mappers.EmployeeMapperBO;
import com.test.ticket.domain.models.EmployeeBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class UpdateEmployeeUseCase {


    private final IEmployee repository;
    private final EmployeeMapperBO mapperBO;

    public UpdateEmployeeUseCase(IEmployee repository, EmployeeMapperBO mapperBO) {
        this.repository = repository;
        this.mapperBO = mapperBO;
    }

    public EmployeeDTO invoke(EmployeeDTO newData,UUID id){
        Optional<EmployeeDTO> existingOpt = repository.getById(id);
        if (existingOpt.isEmpty()) {
            throw new NotFoundException("Funcionário não encontrado");
        }

        EmployeeBO existing = mapperBO.toBO(existingOpt.get());

        if (newData.name() != null) {
            existing.setName(newData.name());
        }
        if (newData.cpf() != null) {
            existing.setCpf(newData.cpf());
        }

        existing.lastUpdate();

        EmployeeDTO updatedDTO = mapperBO.toDTO(existing);
        return repository.update(updatedDTO, id)
                .orElseThrow(() -> new NotFoundException("Erro ao atualizar funcionário"));
    }

}
