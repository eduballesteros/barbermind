package com.barbermind.backend.booking.domain.port.in.barber;

import com.barbermind.backend.booking.domain.model.Barber;

import java.util.List;

public interface SearchBarbersUseCase {

    /**
     * Busca barberos activos cuyo nombre o apellido coincida con el término.
     */

    List<Barber> searchByQuery(String query);
}
