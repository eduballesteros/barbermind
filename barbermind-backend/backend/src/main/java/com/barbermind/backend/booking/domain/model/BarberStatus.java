package com.barbermind.backend.booking.domain.model;

/**
 * Representa el ciclo de estados posibles en los que se puede encontrar un barbero.
 */

public enum BarberStatus {
    ACTIVE,
    INACTIVE,
    BUSY,
    ON_VACATION,
    SICK_LEAVE,
}