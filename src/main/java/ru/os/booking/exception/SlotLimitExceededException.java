package ru.os.booking.exception;

public class SlotLimitExceededException extends RuntimeException {

    public SlotLimitExceededException(int limit) {
        super("Slot limit exceeded (%s).".formatted(limit));
    }
}
