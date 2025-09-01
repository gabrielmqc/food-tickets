package com.test.ticket.infrastructure.mappers;

import com.test.ticket.domain.models.TicketBO;
import com.test.ticket.infrastructure.mysql.entities.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapperEntity {
    TicketMapperEntity INSTANCE = Mappers.getMapper(TicketMapperEntity.class);

    TicketBO toBO(TicketEntity entity);

    TicketEntity toEntity(TicketBO bo);
}
