package ru.os.booking.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors().stream().map(er -> {
            var field = er.getField();
            var message = er.getDefaultMessage();
            return "%s: %s".formatted(field, message);
        }).toList();
        var message = "Invalid request data: %s".formatted(errors);
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request data");
        problem.setDetail("Some arguments contain invalid data");
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request parameter");
        var parameter = e.getName();
        problem.setDetail("Parameter '%s' has an invalid format".formatted(parameter));
        problem.setProperty("parameter", parameter);
        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadableBody(HttpMessageNotReadableException e) {
        var message = e.getMessage();
        log.warn(message);

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request body");
        problem.setDetail("Request body is missing or contains invalid JSON or field values");
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
