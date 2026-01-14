package com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity;

import com.barbermind.backend.booking.domain.model.BarberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Representación física del Barbero en la base de datos
 */
@Entity
@Table(name = "barbers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
            @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date dateOfHire;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BarberStatus status;
}
