package com.barbermind.backend.booking.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Appointment Domain Entity Tests")
class AppointmentTest {

    @Nested
    @DisplayName("Factory method: create()")
    class CreateTests {

        @Test
        @DisplayName("Should create appointment with valid data")
        void shouldCreateAppointmentWithValidData() {
            // Arrange
            UUID customerId = UUID.randomUUID();
            UUID salonId = UUID.randomUUID();
            UUID employeeId = UUID.randomUUID();
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            int durationInMinutes = 30;
            BigDecimal price = new BigDecimal("25.00");
            AppointmentStatus status = AppointmentStatus.PENDING;

            // Act
            Appointment appointment = Appointment.create(
                    customerId, salonId, employeeId, startTime, durationInMinutes, price, status
            );

            // Assert
            assertNotNull(appointment);
            assertNotNull(appointment.getId());
            assertEquals(customerId, appointment.getCustomerId());
            assertEquals(salonId, appointment.getSalonId());
            assertEquals(employeeId, appointment.getEmployeeId());
            assertEquals(startTime, appointment.getStartTime());
            assertEquals(startTime.plusMinutes(durationInMinutes), appointment.getEndTime());
            assertEquals(price, appointment.getPrice());
            assertEquals(AppointmentStatus.PENDING, appointment.getStatus());
        }

        @Test
        @DisplayName("Should generate unique UUID for each appointment")
        void shouldGenerateUniqueUUID() {
            // Arrange
            UUID customerId = UUID.randomUUID();
            UUID salonId = UUID.randomUUID();
            UUID employeeId = UUID.randomUUID();
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            BigDecimal price = new BigDecimal("25.00");

            // Act
            Appointment appointment1 = Appointment.create(
                    customerId, salonId, employeeId, startTime, 30, price, AppointmentStatus.PENDING
            );
            Appointment appointment2 = Appointment.create(
                    customerId, salonId, employeeId, startTime, 30, price, AppointmentStatus.PENDING
            );

            // Assert
            assertNotEquals(appointment1.getId(), appointment2.getId());
        }

        @Test
        @DisplayName("Should calculate endTime correctly based on duration")
        void shouldCalculateEndTimeCorrectly() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.of(2026, 2, 10, 10, 0);
            int durationInMinutes = 45;

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, durationInMinutes, new BigDecimal("30.00"), AppointmentStatus.PENDING
            );

            // Assert
            LocalDateTime expectedEndTime = LocalDateTime.of(2026, 2, 10, 10, 45);
            assertEquals(expectedEndTime, appointment.getEndTime());
        }

        @Test
        @DisplayName("Should always set status to PENDING regardless of input")
        void shouldAlwaysSetStatusToPending() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act
            Appointment appointment1 = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, new BigDecimal("25.00"), AppointmentStatus.CONFIRMED
            );
            Appointment appointment2 = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, new BigDecimal("25.00"), AppointmentStatus.COMPLETED
            );

            // Assert
            assertEquals(AppointmentStatus.PENDING, appointment1.getStatus());
            assertEquals(AppointmentStatus.PENDING, appointment2.getStatus());
        }

        @Test
        @DisplayName("Should throw exception when customerId is null")
        void shouldThrowExceptionWhenCustomerIdIsNull() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            null, UUID.randomUUID(), UUID.randomUUID(),
                            startTime, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("Datos incompletos: cliente, salón y empleado son obligatorios.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when salonId is null")
        void shouldThrowExceptionWhenSalonIdIsNull() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), null, UUID.randomUUID(),
                            startTime, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("Datos incompletos: cliente, salón y empleado son obligatorios.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when employeeId is null")
        void shouldThrowExceptionWhenEmployeeIdIsNull() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), null,
                            startTime, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("Datos incompletos: cliente, salón y empleado son obligatorios.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when startTime is null")
        void shouldThrowExceptionWhenStartTimeIsNull() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            null, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("Datos incompletos: cliente, salón y empleado son obligatorios.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when duration is zero")
        void shouldThrowExceptionWhenDurationIsZero() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            startTime, 0, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("Datos incompletos: cliente, salón y empleado son obligatorios.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when duration is negative")
        void shouldThrowExceptionWhenDurationIsNegative() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            startTime, -10, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("Datos incompletos: cliente, salón y empleado son obligatorios.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when startTime is in the past")
        void shouldThrowExceptionWhenStartTimeIsInThePast() {
            // Arrange
            LocalDateTime pastTime = LocalDateTime.now().minusHours(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            pastTime, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("No se puede reservar en el pasado.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when price is null")
        void shouldThrowExceptionWhenPriceIsNull() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            startTime, 30, null, AppointmentStatus.PENDING
                    )
            );
            assertEquals("El precio no puede ser negativo.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when price is zero")
        void shouldThrowExceptionWhenPriceIsZero() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            startTime, 30, BigDecimal.ZERO, AppointmentStatus.PENDING
                    )
            );
            assertEquals("El precio no puede ser negativo.", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when price is negative")
        void shouldThrowExceptionWhenPriceIsNegative() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> Appointment.create(
                            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                            startTime, 30, new BigDecimal("-10.00"), AppointmentStatus.PENDING
                    )
            );
            assertEquals("El precio no puede ser negativo.", exception.getMessage());
        }

        @Test
        @DisplayName("Should accept minimum valid price")
        void shouldAcceptMinimumValidPrice() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            BigDecimal minimumPrice = new BigDecimal("0.01");

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, minimumPrice, AppointmentStatus.PENDING
            );

            // Assert
            assertNotNull(appointment);
            assertEquals(minimumPrice, appointment.getPrice());
        }
    }

    @Nested
    @DisplayName("Factory method: reconstruct()")
    class ReconstructTests {

        @Test
        @DisplayName("Should reconstruct appointment with all fields")
        void shouldReconstructAppointmentWithAllFields() {
            // Arrange
            UUID id = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            UUID salonId = UUID.randomUUID();
            UUID employeeId = UUID.randomUUID();
            LocalDateTime startTime = LocalDateTime.of(2026, 2, 10, 10, 0);
            LocalDateTime endTime = LocalDateTime.of(2026, 2, 10, 10, 30);
            BigDecimal price = new BigDecimal("25.00");
            AppointmentStatus status = AppointmentStatus.CONFIRMED;

            // Act
            Appointment appointment = Appointment.reconstruct(
                    id, customerId, salonId, employeeId, startTime, endTime, price, status
            );

            // Assert
            assertNotNull(appointment);
            assertEquals(id, appointment.getId());
            assertEquals(customerId, appointment.getCustomerId());
            assertEquals(salonId, appointment.getSalonId());
            assertEquals(employeeId, appointment.getEmployeeId());
            assertEquals(startTime, appointment.getStartTime());
            assertEquals(endTime, appointment.getEndTime());
            assertEquals(price, appointment.getPrice());
            assertEquals(status, appointment.getStatus());
        }

        @Test
        @DisplayName("Should reconstruct appointment with different statuses")
        void shouldReconstructAppointmentWithDifferentStatuses() {
            // Arrange
            UUID id = UUID.randomUUID();
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusMinutes(30);
            BigDecimal price = new BigDecimal("25.00");

            // Act & Assert
            Appointment pending = Appointment.reconstruct(
                    id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, endTime, price, AppointmentStatus.PENDING
            );
            assertEquals(AppointmentStatus.PENDING, pending.getStatus());

            Appointment confirmed = Appointment.reconstruct(
                    id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, endTime, price, AppointmentStatus.CONFIRMED
            );
            assertEquals(AppointmentStatus.CONFIRMED, confirmed.getStatus());

            Appointment completed = Appointment.reconstruct(
                    id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, endTime, price, AppointmentStatus.COMPLETED
            );
            assertEquals(AppointmentStatus.COMPLETED, completed.getStatus());

            Appointment cancelled = Appointment.reconstruct(
                    id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, endTime, price, AppointmentStatus.CANCELLED
            );
            assertEquals(AppointmentStatus.CANCELLED, cancelled.getStatus());

            Appointment noShow = Appointment.reconstruct(
                    id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, endTime, price, AppointmentStatus.NO_SHOW
            );
            assertEquals(AppointmentStatus.NO_SHOW, noShow.getStatus());
        }

        @Test
        @DisplayName("Should preserve exact UUID when reconstructing")
        void shouldPreserveExactUUIDWhenReconstructing() {
            // Arrange
            UUID specificId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusMinutes(30);

            // Act
            Appointment appointment = Appointment.reconstruct(
                    specificId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, endTime, new BigDecimal("25.00"), AppointmentStatus.PENDING
            );

            // Assert
            assertEquals(specificId, appointment.getId());
        }

        @Test
        @DisplayName("Should preserve exact timestamps when reconstructing")
        void shouldPreserveExactTimestampsWhenReconstructing() {
            // Arrange
            LocalDateTime specificStart = LocalDateTime.of(2026, 3, 15, 14, 30, 0);
            LocalDateTime specificEnd = LocalDateTime.of(2026, 3, 15, 15, 15, 0);

            // Act
            Appointment appointment = Appointment.reconstruct(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    specificStart, specificEnd, new BigDecimal("25.00"), AppointmentStatus.PENDING
            );

            // Assert
            assertEquals(specificStart, appointment.getStartTime());
            assertEquals(specificEnd, appointment.getEndTime());
        }
    }

    @Nested
    @DisplayName("Getters")
    class GettersTests {

        @Test
        @DisplayName("Should return immutable UUID")
        void shouldReturnImmutableUUID() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
            );

            // Act
            UUID id1 = appointment.getId();
            UUID id2 = appointment.getId();

            // Assert
            assertEquals(id1, id2);
            assertSame(id1, id2);
        }

        @Test
        @DisplayName("Should return all fields correctly")
        void shouldReturnAllFieldsCorrectly() {
            // Arrange
            UUID customerId = UUID.randomUUID();
            UUID salonId = UUID.randomUUID();
            UUID employeeId = UUID.randomUUID();
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            int durationInMinutes = 30;
            BigDecimal price = new BigDecimal("25.00");

            Appointment appointment = Appointment.create(
                    customerId, salonId, employeeId, startTime, durationInMinutes, price, AppointmentStatus.PENDING
            );

            // Act & Assert
            assertAll("All getters should return correct values",
                    () -> assertNotNull(appointment.getId()),
                    () -> assertEquals(customerId, appointment.getCustomerId()),
                    () -> assertEquals(salonId, appointment.getSalonId()),
                    () -> assertEquals(employeeId, appointment.getEmployeeId()),
                    () -> assertEquals(startTime, appointment.getStartTime()),
                    () -> assertEquals(startTime.plusMinutes(durationInMinutes), appointment.getEndTime()),
                    () -> assertEquals(price, appointment.getPrice()),
                    () -> assertEquals(AppointmentStatus.PENDING, appointment.getStatus())
            );
        }
    }

    @Nested
    @DisplayName("Business Rules")
    class BusinessRulesTests {

        @Test
        @DisplayName("Should accept appointment starting exactly now")
        void shouldAcceptAppointmentStartingExactlyNow() {
            // Arrange
            // LocalDateTime.now() puede variar ligeramente, así que usamos un tiempo futuro seguro
            LocalDateTime startTime = LocalDateTime.now().plusSeconds(1);

            // Act & Assert
            assertDoesNotThrow(() -> Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
            ));
        }

        @Test
        @DisplayName("Should accept various valid durations")
        void shouldAcceptVariousValidDurations() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            int[] validDurations = {15, 30, 45, 60, 90, 120, 180};

            // Act & Assert
            for (int duration : validDurations) {
                assertDoesNotThrow(() -> Appointment.create(
                        UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                        startTime, duration, new BigDecimal("25.00"), AppointmentStatus.PENDING
                ), "Should accept duration: " + duration + " minutes");
            }
        }

        @Test
        @DisplayName("Should accept various valid prices")
        void shouldAcceptVariousValidPrices() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            String[] validPrices = {"0.01", "10.00", "25.50", "100.00", "999.99"};

            // Act & Assert
            for (String priceStr : validPrices) {
                BigDecimal price = new BigDecimal(priceStr);
                assertDoesNotThrow(() -> Appointment.create(
                        UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                        startTime, 30, price, AppointmentStatus.PENDING
                ), "Should accept price: " + priceStr);
            }
        }

        @Test
        @DisplayName("Should handle appointments spanning multiple hours")
        void shouldHandleAppointmentsSpanningMultipleHours() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.of(2026, 2, 10, 9, 0);
            int durationInMinutes = 240; // 4 hours

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, durationInMinutes, new BigDecimal("100.00"), AppointmentStatus.PENDING
            );

            // Assert
            LocalDateTime expectedEndTime = LocalDateTime.of(2026, 2, 10, 13, 0);
            assertEquals(expectedEndTime, appointment.getEndTime());
        }

        @Test
        @DisplayName("Should handle appointments crossing midnight")
        void shouldHandleAppointmentsCrossingMidnight() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.of(2026, 2, 10, 23, 30);
            int durationInMinutes = 60; // 1 hour

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, durationInMinutes, new BigDecimal("50.00"), AppointmentStatus.PENDING
            );

            // Assert
            LocalDateTime expectedEndTime = LocalDateTime.of(2026, 2, 11, 0, 30);
            assertEquals(expectedEndTime, appointment.getEndTime());
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very long appointments")
        void shouldHandleVeryLongAppointments() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            int veryLongDuration = 1440; // 24 hours

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, veryLongDuration, new BigDecimal("500.00"), AppointmentStatus.PENDING
            );

            // Assert
            assertNotNull(appointment);
            assertEquals(startTime.plusMinutes(veryLongDuration), appointment.getEndTime());
        }

        @Test
        @DisplayName("Should handle appointments far in the future")
        void shouldHandleAppointmentsFarInTheFuture() {
            // Arrange
            LocalDateTime farFuture = LocalDateTime.now().plusYears(1);

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    farFuture, 30, new BigDecimal("25.00"), AppointmentStatus.PENDING
            );

            // Assert
            assertNotNull(appointment);
            assertEquals(farFuture, appointment.getStartTime());
        }

        @Test
        @DisplayName("Should handle very high prices")
        void shouldHandleVeryHighPrices() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            BigDecimal highPrice = new BigDecimal("9999.99");

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, highPrice, AppointmentStatus.PENDING
            );

            // Assert
            assertNotNull(appointment);
            assertEquals(highPrice, appointment.getPrice());
        }

        @Test
        @DisplayName("Should handle prices with many decimal places")
        void shouldHandlePricesWithManyDecimalPlaces() {
            // Arrange
            LocalDateTime startTime = LocalDateTime.now().plusDays(1);
            BigDecimal precisePrice = new BigDecimal("25.123456");

            // Act
            Appointment appointment = Appointment.create(
                    UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                    startTime, 30, precisePrice, AppointmentStatus.PENDING
            );

            // Assert
            assertNotNull(appointment);
            assertEquals(precisePrice, appointment.getPrice());
        }
    }
}