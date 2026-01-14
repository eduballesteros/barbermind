package com.barbermind.backend.booking.application.dto;

import com.barbermind.backend.booking.domain.model.BarberStatus;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.UUID;


public record CreateBarberCommand(

        @NotBlank(message = "El nombre no puede estar vacío")
        String firstName,

        @NotBlank(message = "El apellido no puede estar vacío")
        String lastName,

        @Email(message = "Formato de email inválido")
        @NotBlank
        String email,

        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotNull
        @PastOrPresent(message = "La fecha de contratación no puede ser futura")
        Date dateOfHire,

        @NotNull
        BarberStatus status)

{
}
