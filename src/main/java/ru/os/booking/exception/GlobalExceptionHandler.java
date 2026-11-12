package ru.os.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingAlreadyCancelledException.class)
    public ProblemDetail handleBookingAlreadyCancelledException(BookingAlreadyCancelledException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Booking already cancelled");
        problem.setProperty("id", e.getId());
        return problem;
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ProblemDetail handleBookingNotFoundException(BookingNotFoundException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Booking not found");
        problem.setProperty("id", e.getId());
        return problem;
    }

    @ExceptionHandler(InvalidBookingDataException.class)
    public ProblemDetail handleInvalidInputDataException(InvalidBookingDataException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid input data");
        problem.setDetail("Some fields contain invalid data");
        problem.setProperty("errors", e.getErrors());
        return problem;
    }

    @ExceptionHandler(SlotLimitExceededException.class)
    public ProblemDetail handleSlotLimitExceededException(SlotLimitExceededException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Slot limit exceeded");
        problem.setDetail("No available slots for this date and time");
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception e, HttpServletRequest request) {
        var method = request.getMethod();
        var uri = request.getRequestURI();
        log.error("Unexpected exception: {} {}", method, uri, e);

        var problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Internal server error");
        problem.setDetail("Unexpected error occurred");
        return problem;
    }
}
