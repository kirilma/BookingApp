package ru.os.booking.exception;

import java.util.List;

public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(List<String> errors) {
        super(errors.toString());
    }
}
