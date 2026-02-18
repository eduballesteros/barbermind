package com.barbermind.backend.booking.domain.port.in.appointment;

import java.util.UUID;

/**
 * Input Port (Puerto de Entrada).
 * Define los casos de uso (acciones) que el mundo exterior puede solicitar al dominio.
 */
public interface CancelAppointmentUseCase {

    /**
     * Procesa la cancelación de una cita.
     * @param appointmentId Identificador único de la cita a cancelar.
     * @return El UUID de la cita recién cancelada.
     */
    UUID cancelAppointment(UUID appointmentId);
}
