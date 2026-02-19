package com.barbermind.backend.booking.domain.port.in.appointment;

import com.barbermind.backend.booking.application.dto.AppointmentSearchCriteria;
import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.model.Barber;

import java.util.List;

public interface SearchAppointmentUseCase {
    List<Appointment> search(AppointmentSearchCriteria criteria);
}
