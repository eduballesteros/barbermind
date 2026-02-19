package com.barbermind.backend.booking.application.service;

import com.barbermind.backend.booking.application.dto.AppointmentSearchCriteria;
import com.barbermind.backend.booking.application.dto.CreateAppointmentCommand;
import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.port.in.appointment.CancelAppointmentUseCase;
import com.barbermind.backend.booking.domain.port.in.appointment.CreateAppointmentUseCase;
import com.barbermind.backend.booking.domain.port.in.appointment.SearchAppointmentUseCase;
import com.barbermind.backend.booking.domain.port.out.AppointmentRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService implements CreateAppointmentUseCase, CancelAppointmentUseCase, SearchAppointmentUseCase {

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
               command.price(),
               command.status()
       );

       Appointment savedAppointment = appointmentRepositoryPort.save(appointment);

       return savedAppointment.getId();
   }

   @Override
   @Transactional
   public UUID cancelAppointment(UUID id) {

       if (!appointmentRepositoryPort.existsById(id)) {
           throw new IllegalArgumentException("No se puede cancelar: Cita no encontrada con ID: " + id);
       }

       Appointment appointment = appointmentRepositoryPort.findById(id)
               .orElseThrow(() -> new IllegalStateException("Error inesperado: la cita desapareció durante el proceso."));

        Appointment cancelledAppointment = appointment.cancel();

       Appointment savedAppointment = appointmentRepositoryPort.save(cancelledAppointment);

       return savedAppointment.getId();
   }


    @Override
    public List<Appointment> search(AppointmentSearchCriteria criteria) {
        return appointmentRepositoryPort.findAll()
                .stream()
                .filter(app -> criteria.employeeId() == null || app.getEmployeeId().equals(criteria.employeeId()))
                .filter(app -> criteria.date() == null || app.getStartTime().toLocalDate().equals(criteria.date()))
                .filter(app -> criteria.status() == null || app.getStatus() ==criteria.status())
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .toList();
    }
}

