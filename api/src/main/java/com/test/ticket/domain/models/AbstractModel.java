package com.test.ticket.domain.models;

import com.test.ticket.domain.enums.Situation;
import com.test.ticket.domain.exceptions.BusinessRuleException;
import com.test.ticket.domain.interfaces.Creation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractModel implements Creation {

    private final UUID id;
    private Situation situation;
    private LocalDateTime alterationDate;

    public void activate() {
        this.situation = Situation.A;
        lastUpdate();
    }

    public void deactivate() {
        this.situation = Situation.I;
        lastUpdate();
    }

    public void lastUpdate () {
        alterationDate = LocalDateTime.now();
    }

    @Override
    public void validateForCreation() throws BusinessRuleException {
        if (situation == Situation.I) {
            throw new BusinessRuleException("A situação inicial não pode ser Inativa");
        }
    }

}
