package com.barbermind.backend.booking.application.service;

import com.barbermind.backend.booking.application.dto.CreateAppointmentCommand;
import com.barbermind.backend.booking.domain.port.in.CreateAppointmentUseCase;
import java.util.UUID;

public class Appointment implements CreateAppointmentUseCase {

    @Override
    public UUID createAppointment(CreateAppointmentCommand command) {
        return null;
    }
}
