package com.barbermind.backend.booking.domain.port.in;

import com.barbermind.backend.booking.application.dto.CreateBarberCommand;

import java.util.UUID;

/**
 * Puerto de entrada que define el caso de uso para la creación de nuevos barberos.
 */
public interface CreateBarberUseCase {
    /**
     * Procesa la creación de un barbero en el sistema.
     * * @param command Objeto con los datos validados para el registro.
     * @return El identificador único (UUID) del barbero recién creado.
     */
    UUID createBarber(CreateBarberCommand command);

}
