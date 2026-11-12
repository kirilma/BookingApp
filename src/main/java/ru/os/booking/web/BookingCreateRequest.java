package ru.os.booking.web;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BookingCreateRequest(
        @Schema(example = "Анна Петрова") @Pattern(regexp = "[A-Za-zА-Яа-яЁё -]+") @Size(min = 2, max = 50) @NotBlank String name,
        @Schema(example = "+7 (999) 123-45-67") @NotBlank String phone,
        @NotNull LocalDate bookingDate,
        @Schema(type = "string", example = "19:00") @JsonFormat(pattern = "HH:mm") @NotNull LocalTime bookingTime,
        @NotNull @Min(1) Integer guests
) {}
