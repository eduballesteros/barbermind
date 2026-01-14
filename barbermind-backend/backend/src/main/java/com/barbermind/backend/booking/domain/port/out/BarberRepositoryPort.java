package com.barbermind.backend.booking.domain.port.out;

import com.barbermind.backend.booking.domain.model.Barber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para la persistencia de la entidad {@link Barber}
 */
public interface BarberRepositoryPort {

    Barber save (Barber barber);

    Optional<Barber> findById (UUID id);

    Optional<Barber> findByName (String name);

    Optional<Barber> findByEmail (String email);

    /**
     * @return Listado de barberos con estado {@code ACTIVE}.
     */
    List<Barber> findAllActive();
}
