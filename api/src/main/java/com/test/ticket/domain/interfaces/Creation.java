package com.test.ticket.domain.interfaces;

import com.test.ticket.domain.exceptions.BusinessRuleException;

public interface Creation {
    void validateForCreation() throws BusinessRuleException;
}
