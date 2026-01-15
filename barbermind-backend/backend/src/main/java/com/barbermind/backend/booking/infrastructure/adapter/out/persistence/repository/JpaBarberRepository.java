package com.barbermind.backend.booking.infrastructure.adapter.out.persistence.repository;

import com.barbermind.backend.booking.domain.model.BarberStatus;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaBarberRepository extends JpaRepository<BarberEntity, UUID> {

    Optional<BarberEntity> findByEmail(String email);

    Optional<BarberEntity> findById(UUID Id);

    Optional<BarberEntity> findByFirstName(String firstName);

    List<BarberEntity> findByStatus(BarberStatus status);
}
