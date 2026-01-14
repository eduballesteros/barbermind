package com.barbermind.backend.booking.application.service;

import com.barbermind.backend.booking.application.dto.CreateBarberCommand;
import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.domain.port.in.CreateBarberUseCase;
import com.barbermind.backend.booking.domain.port.out.BarberRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Orquestador de aplicación para la gestión de barberos.
 * * Implementa el puerto de entrada (Primary Port) coordinando la transición
 * entre los datos de entrada (DTO) y el estado persistente del dominio.
 */
@Service
@RequiredArgsConstructor
public class BarberService implements CreateBarberUseCase {

    private final BarberRepositoryPort barberRepositoryPort;

    /**
     * Coordina el proceso de alta de un nuevo barbero.
     * * El método delega la lógica de construcción y validación invariante al
     * agregado de dominio {@link Barber}, asegurando que la persistencia
     * solo ocurra si el estado resultante es válido.
     *
     * @param command Datos de entrada validados en capa de transporte.
     * @return Identificador único del recurso creado.
     */
    @Override
    @Transactional
    public UUID createBarber(CreateBarberCommand command) {
        // Asegúrate de que el orden coincida exactamente con el constructor/método de Barber
        Barber barber = Barber.create(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.password(),
                command.dateOfHire(),
                command.status()
        );

        Barber savedBarber = barberRepositoryPort.save(barber);
        return savedBarber.getid();
    }

}
