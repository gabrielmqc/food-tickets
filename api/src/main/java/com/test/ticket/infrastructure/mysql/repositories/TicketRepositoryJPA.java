package com.test.ticket.infrastructure.mysql.repositories;

import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepositoryJPA extends JpaRepository<TicketEntity, UUID > {

    @Query("SELECT t FROM TicketEntity t WHERE t.employee.id = :employeeId")
    List<TicketEntity> findByEmployeeId(@Param("employeeId") UUID employeeId);

    @Query("SELECT t FROM TicketEntity t WHERE t.alterationDate BETWEEN :startDate AND :endDate")
    List<TicketEntity> findByPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT t FROM TicketEntity t " +
            "WHERE t.employee.id = :employeeId " +
            "AND t.alterationDate BETWEEN :startDate AND :endDate")
    List<TicketEntity> findByEmployeeAndPeriod(
            @Param("employeeId") UUID employeeId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
