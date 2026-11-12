package ru.os.booking.exception;

import lombok.Getter;

@Getter
public class BookingAlreadyCancelledException extends RuntimeException {

    private final long id;

    public BookingAlreadyCancelledException(long id) {
        super("Booking with ID = %s already cancelled.".formatted(id));
        this.id = id;
    }
}
