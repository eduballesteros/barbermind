package com.barbermind.backend.booking.application.service;

import com.barbermind.backend.booking.application.dto.CreateBarberCommand;
import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.domain.port.in.barber.CreateBarberUseCase;
import com.barbermind.backend.booking.domain.port.in.barber.DeleteBarberUseCase;
import com.barbermind.backend.booking.domain.port.in.barber.SearchBarbersUseCase;
import com.barbermind.backend.booking.domain.port.out.BarberRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Orquestador de aplicación para la gestión de barberos.
 * * Implementa el puerto de entrada (Primary Port) coordinando la transición
 * entre los datos de entrada (DTO) y el estado persistente del dominio.
 */
@Service
@RequiredArgsConstructor
public class BarberService implements CreateBarberUseCase, DeleteBarberUseCase, SearchBarbersUseCase {

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

    /**
     * Busca barberos activos basándose en un criterio de búsqueda.
     * El filtrado se realiza sobre el nombre y el apellido, siendo insensible a mayúsculas.
     * Los resultados se devuelven ordenados alfabéticamente por nombre.
     *
     * @param query Término de búsqueda (nombre o apellido).
     * @return Lista de barberos filtrados y ordenados.
     */

    @Override
    public List<Barber> searchByQuery(String query) {

        return barberRepositoryPort.findAllActive()
                .stream()
                .filter(barber ->
                        barber.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                                barber.getLastName().toLowerCase().contains(query.toLowerCase())
                )
                .sorted(Comparator.comparing(Barber::getFirstName))
                .toList();
    }
}
