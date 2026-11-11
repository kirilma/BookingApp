package ru.os.booking.web;

import ru.os.booking.entity.BookingStatus;

public record BookingCreatedResponse(
        Long id,
        BookingStatus status
) {}
