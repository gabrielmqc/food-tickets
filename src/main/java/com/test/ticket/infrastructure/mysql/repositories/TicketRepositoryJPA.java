package com.test.ticket.infrastructure.mysql.repositories;

import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepositoryJPA extends JpaRepository<UUID, TicketEntity> {
}
