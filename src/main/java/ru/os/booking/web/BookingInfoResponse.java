package ru.os.booking.web;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.os.booking.entity.BookingStatus;

public record BookingInfoResponse(
        @Schema(type = "integer", example = "12") Long id,
        @Schema(example = "Анна Петрова") String name,
        @Schema(example = "+7 (999) 123-45-67") String phone,
        LocalDate bookingDate,
        @Schema(type = "string", example = "19:00") @JsonFormat(pattern = "HH:mm") LocalTime bookingTime,
        @Schema(type = "integer", example = "12") Integer guests,
        BookingStatus status,
        Instant createdAt
) {}
