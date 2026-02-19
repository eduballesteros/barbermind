package com.barbermind.backend.booking.application.dto;

import com.barbermind.backend.booking.domain.model.AppointmentStatus;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO para agrupar los criterios de búsqueda de citas.
 * Usamos un record para asegurar la inmutabilidad.
 */
public record AppointmentSearchCriteria(
        UUID employeeId,
        LocalDate date,
        AppointmentStatus status
) {}
