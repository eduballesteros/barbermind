package com.barbermind.backend.booking.domain.port.out;

import com.barbermind.backend.booking.domain.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepositoryPort {

    // Guardar una cita (sirve para Crear nueva o Actualizar existente)
    Appointment save(Appointment appointment);

    // Buscar una cita por su ID (devuelve Optional por si no existe)
    Optional<Appointment> findById(UUID id);

    // Buscar citas de un empleado en un rango de fechas.
    List<Appointment> findByEmployeeAndDataRange(UUID employeeId, LocalDate startDate, LocalDate endDate);

}
