package ru.os.booking.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class InvalidBookingDataException extends RuntimeException {

    private final List<String> errors;

    public InvalidBookingDataException(List<String> errors) {
        super("Invalid booking data: " + errors.toString());
        this.errors = errors;
    }
}
