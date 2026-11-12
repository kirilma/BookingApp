package ru.os.booking.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotLimitExceededException extends RuntimeException {

    public SlotLimitExceededException(int limit, LocalDate date, LocalTime time) {
        super("Slot limit (%s) exceeded for %s at %s.".formatted(limit, date, time));
    }
}
