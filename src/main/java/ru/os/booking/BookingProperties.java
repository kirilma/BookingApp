package ru.os.booking;

import java.time.LocalTime;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "booking.properties")
public record BookingProperties(
        @NotNull @Min(1) int days,
        @NotNull @Min(1) int guests,
        @NotNull @Min(1) int bookings,
        @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime open,
        @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime closed
) {}
