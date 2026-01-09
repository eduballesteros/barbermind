package domain.port.in;

import application.dto.CreateAppointmentCommand;
import java.util.UUID;

/**
 * Input Port (Puerto de Entrada).
 * Define los casos de uso (acciones) que el mundo exterior puede solicitar al dominio.
 */
public interface CreateAppointmentUseCase {
    UUID createAppointment(CreateAppointmentCommand command);
}
