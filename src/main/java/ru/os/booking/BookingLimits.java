package ru.os.booking;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;

@Validated
@ConfigurationProperties(prefix = "booking.limits")
public record BookingLimits(
        @Min(1) int days,
        @Min(1) int guests,
        @Min(1) int slots
) {}
