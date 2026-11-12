package ru.os.booking.exception;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends RuntimeException {

    private final long id;

    public BookingNotFoundException(long id) {
        super("Booking with ID = %s not found.".formatted(id));
        this.id = id;
    }
}
