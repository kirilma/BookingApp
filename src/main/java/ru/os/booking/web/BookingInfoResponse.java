package ru.os.booking.web;

import java.time.LocalDate;
import java.time.LocalTime;

import ru.os.booking.entity.BookingStatus;

public record BookingInfoResponse(
        Long id,
        String name,
        String phone,
        LocalDate date,
        LocalTime time,
        BookingStatus status,
        Integer guests
) {}
