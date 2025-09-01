package com.test.ticket.infrastructure.mysql.repositories;

import com.test.ticket.infrastructure.mysql.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeRepositoryJPA extends JpaRepository<EmployeeEntity, UUID > {
}
