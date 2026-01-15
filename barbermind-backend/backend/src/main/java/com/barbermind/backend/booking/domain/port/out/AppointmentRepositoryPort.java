package com.barbermind.backend.booking.domain.port.out;

import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.AppointmentEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para la persistencia de la entidad {@link Appointment}
 */
public interface AppointmentRepositoryPort {


    Appointment save(Appointment appointment);

    Optional<Appointment> findById(UUID id);

    /**
     * @return Listado de empleados y sus citas en dos fechas concretas.
     */
    List<Appointment> findByEmployeeIdAndStartTimeBetween(
            UUID employeeId,
            LocalDateTime start,
            LocalDateTime end
    );

    Optional<Appointment> findByCustomerId(UUID customerId);

    Optional<Appointment> findBySalonId(UUID salonId);

    Optional<Appointment> findByEmployeeId(UUID employeeId);

    Optional<Appointment> findByStartTime(LocalDateTime startTime);

}
