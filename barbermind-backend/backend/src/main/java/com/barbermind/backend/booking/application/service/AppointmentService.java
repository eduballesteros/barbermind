package com.barbermind.backend.booking.application.service;

import com.barbermind.backend.booking.application.dto.CreateAppointmentCommand;
import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.port.in.CreateAppointmentUseCase;
import com.barbermind.backend.booking.domain.port.out.AppointmentRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService implements CreateAppointmentUseCase {

   private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    @Transactional
    public UUID createAppointment(CreateAppointmentCommand command) {

       Appointment appointment = Appointment.create(
               command.customerId(),
               command.salonId(),
               command.employeeId(),
               command.startTime(),
               command.durationInMinutes(),
               command.price()
       );

       Appointment savedAppointment = appointmentRepositoryPort.save(appointment);

       return savedAppointment.getId();
   }
}

