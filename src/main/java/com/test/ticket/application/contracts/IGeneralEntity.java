package com.test.ticket.application.contracts;

import java.util.List;
import java.util.UUID;

public interface IGeneralEntity <T,D>{

    T create ( D request);

    T update (D request, UUID id);

    List<T> getAll();

    void save(D request);

}
