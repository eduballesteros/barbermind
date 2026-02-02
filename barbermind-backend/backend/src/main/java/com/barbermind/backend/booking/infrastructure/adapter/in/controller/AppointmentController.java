package com.barbermind.backend.booking.infrastructure.adapter.in.controller;

import com.barbermind.backend.booking.application.dto.CreateAppointmentCommand;
import com.barbermind.backend.booking.domain.port.in.CreateAppointmentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;

    @PostMapping
    public ResponseEntity<UUID> createAppointment(@Valid @RequestBody CreateAppointmentCommand command){
        UUID id = createAppointmentUseCase.createAppointment(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }

}
