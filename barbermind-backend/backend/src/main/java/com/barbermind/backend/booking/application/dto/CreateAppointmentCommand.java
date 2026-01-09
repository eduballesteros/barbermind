package com.barbermind.backend.booking.application.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Command (DTO): Representa la intención de crear una cita.
 * Contiene solo los datos necesarios que vienen de fuera (API/Frontend).
 */
public record CreateAppointmentCommand(
        UUID customerId,
        UUID salonId,
        UUID employeeId,
        UUID serviceId,
        LocalDateTime startTime,
        int durationInMinutes,
        BigDecimal price
) {
}
