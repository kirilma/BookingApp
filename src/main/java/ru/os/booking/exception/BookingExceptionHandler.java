package ru.os.booking.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@RestControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler(BookingAlreadyCancelledException.class)
    public ProblemDetail handleBookingAlreadyCancelledException(BookingAlreadyCancelledException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Already cancelled");
        problem.setDetail("Booking already cancelled");
        problem.setProperty("id", e.getId());
        return problem;
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ProblemDetail handleBookingNotFoundException(BookingNotFoundException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Not found");
        problem.setDetail("Booking not found");
        problem.setProperty("id", e.getId());
        return problem;
    }

    @ExceptionHandler(InvalidBookingDataException.class)
    public ProblemDetail handleInvalidBookingDataException(InvalidBookingDataException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid booking data");
        problem.setDetail("Some fields contain invalid data");
        problem.setProperty("errors", e.getErrors());
        return problem;
    }

    @ExceptionHandler(SlotLimitExceededException.class)
    public ProblemDetail handleSlotLimitExceededException(SlotLimitExceededException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Slot is fully booked");
        problem.setDetail("No available slots for this date and time");
        return problem;
    }
}
