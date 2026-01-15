package com.barbermind.backend.booking.infrastructure.adapter.out.persistence;

import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.port.out.AppointmentRepositoryPort;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.mapper.AppointmentPersistenceMapper;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.repository.JpaAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación técnica del puerto de salida para PostgreSQL.
 * Actúa como un puente entre el contrato del dominio y Spring Data JPA.
 */

@Component
@RequiredArgsConstructor
public class PostgresAppointmentAdapter implements AppointmentRepositoryPort {

    private final JpaAppointmentRepository jpaRepository;
    private final AppointmentPersistenceMapper mapper;

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = mapper.toEntity(appointment);
        AppointmentEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Appointment> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findByEmployeeIdAndStartTimeBetween(UUID employeeId, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByEmployeeIdAndStartTimeBetween(employeeId, start, end)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }


    @Override
    public Optional<Appointment> findByCustomerId(UUID customerId) {
        return jpaRepository.findByCustomerId(customerId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Appointment> findBySalonId(UUID salonId) {
        return  jpaRepository.findBySalonId(salonId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Appointment> findByEmployeeId(UUID employeeId) {
        return  jpaRepository.findByEmployeeId(employeeId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Appointment> findByStartTime(LocalDateTime startTime) {
        return  jpaRepository.findByStartTime(startTime)
                .map(mapper::toDomain);
    }
}
