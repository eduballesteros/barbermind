package com.barbermind.backend.booking.infrastructure.adapter.out.persistence.mapper;

import com.barbermind.backend.booking.domain.model.Barber;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.BarberEntity;
import org.springframework.stereotype.Component;

@Component
public class BarberPersistenceMapper {

    public BarberEntity toEntity(Barber domain){

        if(domain == null) return null;

        return new BarberEntity(
                domain.getid(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getDateOfHire(),
                domain.getStatus()
        );
    }

    public Barber toDomain (BarberEntity entity){

        if(entity == null) return null;

        return Barber.reconstruct(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getDateOfHire(),
                entity.getStatus()
        );
    }
}


