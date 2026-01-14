package com.barbermind.backend.booking.infrastructure.adapter.out.persistence;

import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.domain.model.BarberStatus;
import com.barbermind.backend.booking.domain.port.out.BarberRepositoryPort;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.BarberEntity;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.mapper.BarberPersistenceMapper;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.repository.JpaBarberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación técnica del puerto de salida para PostgreSQL.
 * Actúa como un puente entre el contrato del dominio y Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class PostgresBarberAdapter implements BarberRepositoryPort {

    private final JpaBarberRepository jpaRepository;
    private final BarberPersistenceMapper mapper;

    @Override
    public Barber save(Barber barber) {
        BarberEntity entity = mapper.toEntity(barber);
        BarberEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Barber> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Barber> findByName(String name) {
        return jpaRepository.findByFirstName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Barber> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public List<Barber> findAllActive() {
        return jpaRepository.findByStatus(BarberStatus.ACTIVE)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}