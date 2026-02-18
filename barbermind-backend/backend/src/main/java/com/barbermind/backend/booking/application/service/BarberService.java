package com.barbermind.backend.booking.application.service;

import com.barbermind.backend.booking.application.dto.CreateBarberCommand;
import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.domain.port.in.barber.CreateBarberUseCase;
import com.barbermind.backend.booking.domain.port.in.barber.DeleteBarberUseCase;
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
public class BarberService implements CreateBarberUseCase, DeleteBarberUseCase {

    private final BarberRepositoryPort barberRepositoryPort;

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


    @Transactional
    @Override
    public UUID deleteBarber(UUID id) {
        if(!barberRepositoryPort.existsById(id))
            throw new IllegalArgumentException("No se puede desactivar: Barbero no encontrado con ID: \" + id");

        Barber barber = barberRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalStateException("Error inesperado: la acción desapareció durante el proceso."));

        Barber deletedBarber = barber.delete();

        Barber savedBarber = barberRepositoryPort.save(deletedBarber);

        return savedBarber.getid();
    }
}
