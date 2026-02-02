package com.barbermind.backend.booking.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Barber Domain Entity Tests")
class BarberTest {

    @Nested
    @DisplayName("Factory method: create()")
    class CreateTests {

        @Test
        @DisplayName("Should create barber with valid data")
        void shouldCreateBarberWithValidData() {
            // Arrange
            String firstName = "Juan";
            String lastName = "Pérez";
            String email = "juan.perez@example.com";
            String password = "password123";
            Date dateOfHire = new Date();
            BarberStatus status = BarberStatus.ACTIVE;

            // Act
            Barber barber = Barber.create(firstName, lastName, email, password, dateOfHire, status);

            // Assert
            assertNotNull(barber);
            assertNotNull(barber.getid());
            assertEquals(firstName, barber.getFirstName());
            assertEquals(lastName, barber.getLastName());
            assertEquals(email, barber.getEmail());
            assertEquals(password, barber.getPassword());
            assertEquals(dateOfHire, barber.getDateOfHire());
            assertEquals(BarberStatus.ACTIVE, barber.getStatus());
        }

        @Test
        @DisplayName("Should generate unique UUID for each barber")
        void shouldGenerateUniqueUUID() {
            // Arrange
            Date dateOfHire = new Date();

            // Act
            Barber barber1 = Barber.create("Juan", "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE);
            Barber barber2 = Barber.create("María", "López", "maria@example.com", "password456", dateOfHire, BarberStatus.ACTIVE);

            // Assert
            assertNotEquals(barber1.getid(), barber2.getid());
        }

        @Test
        @DisplayName("Should throw exception when firstName is null")
        void shouldThrowExceptionWhenFirstNameIsNull() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create(null, "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("El nombre es obligatorio.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when firstName is blank")
        void shouldThrowExceptionWhenFirstNameIsBlank() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("   ", "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("El nombre es obligatorio.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when lastName is null")
        void shouldThrowExceptionWhenLastNameIsNull() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", null, "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("El apellido es obligatorio.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when lastName is blank")
        void shouldThrowExceptionWhenLastNameIsBlank() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", "", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("El apellido es obligatorio.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when email is null")
        void shouldThrowExceptionWhenEmailIsNull() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", "Pérez", null, "password123", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("El email no es válido.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when email is invalid")
        void shouldThrowExceptionWhenEmailIsInvalid() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", "Pérez", "invalidemail", "password123", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("El email no es válido.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when password is null")
        void shouldThrowExceptionWhenPasswordIsNull() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", "Pérez", "juan@example.com", null, dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("La contraseña es muy corta.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when password is too short")
        void shouldThrowExceptionWhenPasswordIsTooShort() {
            // Arrange
            Date dateOfHire = new Date();

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", "Pérez", "juan@example.com", "12345", dateOfHire, BarberStatus.ACTIVE)
            );
            assertEquals("La contraseña es muy corta.", exception.getMessage());
        }

        @Test
        @DisplayName("Should accept password with exactly 6 characters")
        void shouldAcceptPasswordWithExactly6Characters() {
            // Arrange
            Date dateOfHire = new Date();

            // Act
            Barber barber = Barber.create("Juan", "Pérez", "juan@example.com", "123456", dateOfHire, BarberStatus.ACTIVE);

            // Assert
            assertNotNull(barber);
            assertEquals("123456", barber.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when dateOfHire is null")
        void shouldThrowExceptionWhenDateOfHireIsNull() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Barber.create("Juan", "Pérez", "juan@example.com", "password123", null, BarberStatus.ACTIVE)
            );
            assertEquals("La fecha de contratación es obligatoria.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Factory method: reconstruct()")
    class ReconstructTests {

        @Test
        @DisplayName("Should reconstruct barber with all fields")
        void shouldReconstructBarberWithAllFields() {
            // Arrange
            UUID id = UUID.randomUUID();
            String firstName = "Juan";
            String lastName = "Pérez";
            String email = "juan.perez@example.com";
            String password = "password123";
            Date dateOfHire = new Date();
            BarberStatus status = BarberStatus.INACTIVE;

            // Act
            Barber barber = Barber.reconstruct(id, firstName, lastName, email, password, dateOfHire, status);

            // Assert
            assertNotNull(barber);
            assertEquals(id, barber.getid());
            assertEquals(firstName, barber.getFirstName());
            assertEquals(lastName, barber.getLastName());
            assertEquals(email, barber.getEmail());
            assertEquals(password, barber.getPassword());
            assertEquals(dateOfHire, barber.getDateOfHire());
            assertEquals(status, barber.getStatus());
        }

        @Test
        @DisplayName("Should reconstruct barber with different statuses")
        void shouldReconstructBarberWithDifferentStatuses() {
            // Arrange
            UUID id = UUID.randomUUID();
            Date dateOfHire = new Date();

            // Act & Assert
            Barber activeBarber = Barber.reconstruct(id, "Juan", "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE);
            assertEquals(BarberStatus.ACTIVE, activeBarber.getStatus());

            Barber busyBarber = Barber.reconstruct(id, "María", "López", "maria@example.com", "password456", dateOfHire, BarberStatus.BUSY);
            assertEquals(BarberStatus.BUSY, busyBarber.getStatus());

            Barber vacationBarber = Barber.reconstruct(id, "Carlos", "García", "carlos@example.com", "password789", dateOfHire, BarberStatus.ON_VACATION);
            assertEquals(BarberStatus.ON_VACATION, vacationBarber.getStatus());
        }

        @Test
        @DisplayName("Should preserve exact UUID when reconstructing")
        void shouldPreserveExactUUIDWhenReconstructing() {
            // Arrange
            UUID specificId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
            Date dateOfHire = new Date();

            // Act
            Barber barber = Barber.reconstruct(specificId, "Juan", "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE);

            // Assert
            assertEquals(specificId, barber.getid());
        }
    }

    @Nested
    @DisplayName("Getters")
    class GettersTests {

        @Test
        @DisplayName("Should return immutable UUID")
        void shouldReturnImmutableUUID() {
            // Arrange
            Date dateOfHire = new Date();
            Barber barber = Barber.create("Juan", "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.ACTIVE);

            // Act
            UUID id1 = barber.getid();
            UUID id2 = barber.getid();

            // Assert
            assertEquals(id1, id2);
            assertSame(id1, id2);
        }

        @Test
        @DisplayName("Should return all fields correctly")
        void shouldReturnAllFieldsCorrectly() {
            // Arrange
            String firstName = "Juan";
            String lastName = "Pérez";
            String email = "juan.perez@example.com";
            String password = "password123";
            Date dateOfHire = new Date();
            BarberStatus status = BarberStatus.ACTIVE;

            Barber barber = Barber.create(firstName, lastName, email, password, dateOfHire, status);

            // Act & Assert
            assertAll("All getters should return correct values",
                    () -> assertNotNull(barber.getid()),
                    () -> assertEquals(firstName, barber.getFirstName()),
                    () -> assertEquals(lastName, barber.getLastName()),
                    () -> assertEquals(email, barber.getEmail()),
                    () -> assertEquals(password, barber.getPassword()),
                    () -> assertEquals(dateOfHire, barber.getDateOfHire()),
                    () -> assertEquals(BarberStatus.ACTIVE, barber.getStatus())
            );
        }
    }

    @Nested
    @DisplayName("Business Rules")
    class BusinessRulesTests {

        @Test
        @DisplayName("Should always set status to ACTIVE when creating new barber")
        void shouldAlwaysSetStatusToActiveWhenCreatingNewBarber() {
            // Arrange
            Date dateOfHire = new Date();

            // Act
            Barber barber1 = Barber.create("Juan", "Pérez", "juan@example.com", "password123", dateOfHire, BarberStatus.INACTIVE);
            Barber barber2 = Barber.create("María", "López", "maria@example.com", "password456", dateOfHire, BarberStatus.BUSY);
            Barber barber3 = Barber.create("Carlos", "García", "carlos@example.com", "password789", dateOfHire, null);

            // Assert
            assertEquals(BarberStatus.ACTIVE, barber1.getStatus(), "Should be ACTIVE regardless of input");
            assertEquals(BarberStatus.ACTIVE, barber2.getStatus(), "Should be ACTIVE regardless of input");
            assertEquals(BarberStatus.ACTIVE, barber3.getStatus(), "Should be ACTIVE even when status is null");
        }

        @Test
        @DisplayName("Should accept valid email formats")
        void shouldAcceptValidEmailFormats() {
            // Arrange
            Date dateOfHire = new Date();
            String[] validEmails = {
                    "test@example.com",
                    "user.name@example.com",
                    "user+tag@example.co.uk",
                    "test123@test-domain.com"
            };

            // Act & Assert
            for (String email : validEmails) {
                assertDoesNotThrow(
                        () -> Barber.create("Juan", "Pérez", email, "password123", dateOfHire, BarberStatus.ACTIVE),
                        "Should accept email: " + email
                );
            }
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle special characters in names")
        void shouldHandleSpecialCharactersInNames() {
            // Arrange
            Date dateOfHire = new Date();

            // Act
            Barber barber = Barber.create("María José", "O'Connor-Smith", "maria@example.com", "password123", dateOfHire, BarberStatus.ACTIVE);

            // Assert
            assertNotNull(barber);
            assertEquals("María José", barber.getFirstName());
            assertEquals("O'Connor-Smith", barber.getLastName());
        }

        @Test
        @DisplayName("Should handle long password")
        void shouldHandleLongPassword() {
            // Arrange
            Date dateOfHire = new Date();
            String longPassword = "a".repeat(100);

            // Act
            Barber barber = Barber.create("Juan", "Pérez", "juan@example.com", longPassword, dateOfHire, BarberStatus.ACTIVE);

            // Assert
            assertNotNull(barber);
            assertEquals(longPassword, barber.getPassword());
        }

        @Test
        @DisplayName("Should handle date of hire in the past")
        void shouldHandleDateOfHireInThePast() {
            // Arrange
            Date pastDate = new Date(System.currentTimeMillis() - 86400000L); // 1 day ago

            // Act
            Barber barber = Barber.create("Juan", "Pérez", "juan@example.com", "password123", pastDate, BarberStatus.ACTIVE);

            // Assert
            assertNotNull(barber);
            assertEquals(pastDate, barber.getDateOfHire());
        }
    }
}