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
    /**
     * @param email
     * @return SELECT * FROM barbers WHERE email = ?
     */
    Optional<BarberEntity> findByEmail(String email);

    /**
     * @param Id
     * @return SELECT * FROM barbers WHERE id = ?
     */
    Optional<BarberEntity> findById(UUID Id);

    /**
     * @param firstName
     * @return SELECT * FROM barbers WHERE firstName = ? AND lastName = ?
     */
    Optional<BarberEntity> findByFirstName(String firstName);

    /**
     * @param status
     * @return SELECT * FROM barbers WHERE status = ?
     */
    List<BarberEntity> findByStatus(BarberStatus status);
}
