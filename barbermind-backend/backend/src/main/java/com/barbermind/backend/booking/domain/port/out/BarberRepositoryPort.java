package com.barbermind.backend.booking.domain.port.out;

import com.barbermind.backend.booking.domain.model.Barber;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para la persistencia de la entidad {@link Barber}
 */
public interface BarberRepositoryPort {

    Barber save (Barber barber);

    Barber findById (Long id);

    Barber findByName (String name);

    /**
     * @return Listado de barberos con estado {@code ACTIVE}.
     */
    List<Barber> findAllActive();
}
