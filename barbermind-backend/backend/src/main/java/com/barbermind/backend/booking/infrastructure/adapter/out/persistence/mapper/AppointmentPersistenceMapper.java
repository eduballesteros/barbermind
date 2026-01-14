package com.barbermind.backend.booking.infrastructure.adapter.out.persistence.mapper;

import com.barbermind.backend.booking.domain.model.Appointment;
import com.barbermind.backend.booking.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentPersistenceMapper {

    public AppointmentEntity toEntity(Appointment appointment){
        if(appointment == null) return null;

        return new AppointmentEntity(
                appointment.getId(),
                appointment.getCustomerId(),
                appointment.getSalonId(),
                appointment.getEmployeeId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getPrice(),
                appointment.getStatus()
        );
    }

    public Appointment toDomain(AppointmentEntity entity){
        if(entity == null) return null;

        return Appointment.reconstruct(
                entity.getId(),
                entity.getCustomerId(),
                entity.getSalonId(),
                entity.getEmployeeId(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getPrice(),
                entity.getStatus()
        );+
    }
}
