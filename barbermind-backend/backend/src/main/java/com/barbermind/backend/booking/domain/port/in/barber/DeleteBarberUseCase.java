package com.barbermind.backend.booking.domain.port.in.barber;

import java.util.UUID;

public interface DeleteBarberUseCase {

    /**
     * Procesa la cancelación de una cita.
     * @param barberId Identificador único de la cita a cancelar.
     * @return El UUID de la cita recién cancelada.
     */

    UUID deleteBarber(UUID id);

}
