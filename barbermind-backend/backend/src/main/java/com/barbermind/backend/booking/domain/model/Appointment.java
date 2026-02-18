package com.barbermind.backend.booking.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {

    // Identificadores (IDs)
    private final UUID id;
    private final UUID customerId;
    private final UUID salonId;
    private final UUID employeeId;

    // Datos temporales
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    // Datos económicos
    private final BigDecimal price;

    // Estado (Este campo cambiará durante la vida de la cita)
    private final AppointmentStatus status;

    // --- Constructor Privado ---
    public Appointment(UUID id, UUID customerId, UUID salonId, UUID employeeId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal price, AppointmentStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.salonId = salonId;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
    }

    public static Appointment create(UUID customerId, UUID salonId, UUID employeeId, LocalDateTime startTime, int durationInMinutes, BigDecimal price, AppointmentStatus status) {

        if (customerId == null || salonId == null || employeeId == null || startTime == null || durationInMinutes <= 0) {
            throw new IllegalArgumentException("Datos incompletos: cliente, salón y empleado son obligatorios.");
        }

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede reservar en el pasado.");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        UUID id = UUID.randomUUID();

        LocalDateTime endTime = startTime.plusMinutes(durationInMinutes);

        return new Appointment(id, customerId, salonId, employeeId, startTime, endTime, price, AppointmentStatus.PENDING);
    }

    public Appointment cancel(){
        if (status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("No se puede cancelar una cita que ya ha sido finalizada.");
        }
        return Appointment.reconstruct(
                this.id,
                this.customerId,
                this.salonId,
                this.employeeId,
                this.startTime,
                this.endTime,
                this.price,
                AppointmentStatus.CANCELLED
        );
    }

    /**
     * Reconstruye la instancia de la entidad a partir de datos provenientes de la capa de persistencia.
     * * A diferencia del método de creación de negocio, este proceso restaura el estado actual
     * del objeto sin disparar validaciones de nuevos registros ni generar identificadores únicos,
     * asegurando la integridad de los datos históricos recuperados.
     */

    public static Appointment reconstruct(UUID id, UUID customerId, UUID salonId, UUID employeeId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal price, AppointmentStatus status){
        return new Appointment(id, customerId, salonId, employeeId, startTime, endTime, price, status);
    }

    // --- Getters (Solo lectura) ---

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getSalonId()  {
        return salonId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
