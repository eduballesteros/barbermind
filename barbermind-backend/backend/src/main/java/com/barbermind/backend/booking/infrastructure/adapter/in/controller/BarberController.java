package com.barbermind.backend.booking.infrastructure.adapter.in.controller;

import com.barbermind.backend.booking.application.dto.CreateBarberCommand;
import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.domain.port.in.barber.CreateBarberUseCase;
import com.barbermind.backend.booking.domain.port.in.barber.DeleteBarberUseCase;
import com.barbermind.backend.booking.domain.port.in.barber.SearchBarbersUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final CreateBarberUseCase createBarberUseCase;
    private final DeleteBarberUseCase deleteBarberUseCase;
    private final SearchBarbersUseCase  searchBarbersUseCase;

    @PostMapping
    public ResponseEntity<UUID> createBarber(@Valid @RequestBody CreateBarberCommand command) {
        UUID id = createBarberUseCase.createBarber(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }

    @PatchMapping("/id/delete")
    public ResponseEntity<UUID>deleteBarber(@PathVariable UUID id) {

        UUID deletedId = deleteBarberUseCase.deleteBarber(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletedId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Barber>> searchBarbers(@RequestParam (name = "q", required = false, defaultValue = "") String query) {

        List<Barber> results = searchBarbersUseCase.searchByQuery(query);

        return ResponseEntity.ok(results);
    }


}

