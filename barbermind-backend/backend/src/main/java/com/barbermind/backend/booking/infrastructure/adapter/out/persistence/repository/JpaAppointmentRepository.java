package com.barbermind.backend.booking.infrastructure.adapter.out.persistence.repository;

import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.model.AppointmentStatus;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    Optional<AppointmentEntity> findById(UUID id);

    /**
     * @return Listado de empleados y sus citas.
     */
    List<AppointmentEntity> findByEmployeeIdAndStartTimeBetween(
            UUID employeeId,
            LocalDateTime start,
            LocalDateTime end
    );

    Optional<AppointmentEntity> findByCustomerId(UUID customerId);

    Optional<AppointmentEntity> findBySalonId(UUID salonId);

    Optional<AppointmentEntity> findByEmployeeId(UUID employeeId);

    Optional<AppointmentEntity> findByStartTime(LocalDateTime startTime);

}
