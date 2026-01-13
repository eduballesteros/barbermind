package com.barbermind.backend.booking.application.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Command (DTO): Representa la intención de crear una cita.
 * Contiene solo los datos necesarios que vienen de fuera (API/Frontend).
 */
public record CreateAppointmentCommand(
        @NotNull(message = "El ID del cliente es obligatorio")
        UUID customerId,

        @NotNull(message = "El ID del salón es obligatorio")
        UUID salonId,

        @NotNull(message = "El ID del barbero es obligatorio")
        UUID employeeId,

        @NotNull(message = "El ID del servicio es obligatorio")
        UUID serviceId,

        @NotNull(message = "La fecha y hora de inicio son obligatorias")
        @Future(message = "La cita debe programarse para una fecha futura")
        LocalDateTime startTime,

        @Positive(message = "La duración debe ser mayor a 0 minutos")
        int durationInMinutes,

        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio no puede ser negativo")
        BigDecimal price
) {}
