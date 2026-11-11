package ru.os.booking.web;

import java.time.Instant;

import ru.os.booking.entity.BookingStatus;

public record BookingCreatedResponse(
        Long id,
        BookingStatus status,
        Instant createdAt
) {}
