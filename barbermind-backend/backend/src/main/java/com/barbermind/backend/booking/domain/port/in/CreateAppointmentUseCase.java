package com.barbermind.backend.booking.domain.port.in;

import com.barbermind.backend.booking.application.dto.CreateAppointmentCommand;
import java.util.UUID;

/**
 * Input Port (Puerto de Entrada).
 * Define los casos de uso (acciones) que el mundo exterior puede solicitar al dominio.
 */
public interface CreateAppointmentUseCase {

    /**
     * Procesa la creación de una cita.
     * @param command Datos necesarios para crear la cita.
     * @return El UUID de la cita recién creada.
     */
    UUID createAppointment(CreateAppointmentCommand command);
}