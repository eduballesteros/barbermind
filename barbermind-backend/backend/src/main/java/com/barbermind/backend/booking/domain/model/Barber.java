package com.barbermind.backend.booking.domain.model;

import java.util.Date;
import java.util.UUID;

public class Barber {

    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfHire;
    private BarberStatus status;

    private Barber(UUID id, String firstName, String lastName, String email, String password, Date dateofHire, BarberStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfHire = dateofHire;
        this.status =BarberStatus.ACTIVE;
    }

    public static Barber create (String firstName, String lastName, String email, String password, Date dateOfHire, BarberStatus status) {

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

    return new Barber(id, firstName, lastName, email, password, dateOfHire, status);

    }

    /**
     * Factory method para reconstruir la entidad desde persistencia.
     * Permite recuperar el estado exacto almacenado en la base de datos.
     */
    public static Barber reconstruct(UUID id, String firstName, String lastName, String email, String password, Date dateOfHire, BarberStatus status) {
        return new Barber(id, firstName, lastName, email, password, dateOfHire, status);
    }
    // Getters (Solo lectura)
    public UUID getid() {
        return id;
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
