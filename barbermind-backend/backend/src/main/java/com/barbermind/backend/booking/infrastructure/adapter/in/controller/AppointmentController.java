package com.barbermind.backend.booking.infrastructure.adapter.in.controller;

import com.barbermind.backend.booking.application.dto.AppointmentSearchCriteria;
import com.barbermind.backend.booking.application.dto.CreateAppointmentCommand;
import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.domain.model.AppointmentStatus;
import com.barbermind.backend.booking.domain.port.in.appointment.CancelAppointmentUseCase;
import com.barbermind.backend.booking.domain.port.in.appointment.CreateAppointmentUseCase;
import com.barbermind.backend.booking.domain.port.in.appointment.SearchAppointmentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final SearchAppointmentUseCase searchAppointmentUseCase;

    @PostMapping
    public ResponseEntity<UUID> createAppointment(@Valid @RequestBody CreateAppointmentCommand command){
        UUID id = createAppointmentUseCase.createAppointment(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<UUID> cancelAppointment(@PathVariable UUID id){

        UUID cancelledID = cancelAppointmentUseCase.cancelAppointment(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cancelledID);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Appointment>> search(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) AppointmentStatus status) {

        AppointmentSearchCriteria criteria = new AppointmentSearchCriteria(employeeId, date, status);
        List<Appointment> results = searchAppointmentUseCase.search(criteria);

        return ResponseEntity.ok(results);
    }
}
