package com.barbermind.backend.booking.domain.model;

import java.util.Date;
import java.util.UUID;

public class Barber {

    private final UUID barberId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfHire;
    private BarberStatus status;

    private Barber(UUID barberId, String firstName, String lastName, String email, String password, Date dateofHire) {
        this.barberId = barberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfHire = dateofHire;
        this.status =BarberStatus.ACTIVE;
    }

    public static Barber create (String firstName, String lastName, String email, String password, Date dateOfHire) {

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email no es válido.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña es muy corta.");
        }
        if (dateOfHire == null) {
            throw new IllegalArgumentException("La fecha de contratación es obligatoria.");
        }

    UUID id = UUID.randomUUID();

    return new Barber(id, firstName, lastName, email, password, dateOfHire);

    }

    // Getters (Solo lectura)
    public UUID getBarberId() {
        return barberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfHire() {
        return dateOfHire;
    }

    public BarberStatus getStatus() {
        return status;
    }

}
