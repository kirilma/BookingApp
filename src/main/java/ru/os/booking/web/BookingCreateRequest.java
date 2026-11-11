package ru.os.booking.web;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingCreateRequest(
        String name,
        String phone,
        LocalDate date,
        LocalTime time,
        Integer guests
) {}
