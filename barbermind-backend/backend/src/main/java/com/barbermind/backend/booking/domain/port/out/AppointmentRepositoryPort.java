package com.barbermind.backend.booking.domain.port.out;

import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.model.Barber;

import java.time.LocalDate;
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
     * @return Listado de empleados y sus citas.
     */
    List<Appointment> findByEmployeeAndDataRange(UUID employeeId, LocalDate startDate, LocalDate endDate);

}
