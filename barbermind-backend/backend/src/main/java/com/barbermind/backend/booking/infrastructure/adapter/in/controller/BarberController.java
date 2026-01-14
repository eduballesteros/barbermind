package com.barbermind.backend.booking.infrastructure.adapter.in.controller;

import com.barbermind.backend.booking.application.dto.CreateBarberCommand;
import com.barbermind.backend.booking.domain.port.in.CreateBarberUseCase;
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
@RequestMapping("api/v1/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final CreateBarberUseCase createBarberUseCase;

    @PostMapping
    public ResponseEntity<UUID> createBarber(@Valid @RequestBody CreateBarberCommand command) {
        UUID id = createBarberUseCase.createBarber(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }
}

