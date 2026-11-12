package ru.os.booking.component;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.os.booking.web.BookingCreateRequest;
import ru.os.booking.web.BookingCreatedResponse;
import ru.os.booking.web.BookingInfoResponse;
import ru.os.booking.web.BookingListResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
@RestController
public class BookingController {

    private final BookingService service;
    private final BookingMapper mapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookingCreatedResponse create(@RequestBody BookingCreateRequest request) {
        var name = request.name();
        var phone = request.phone();
        var date = request.date();
        var time = request.time();
        var guests = request.guests();
        var booking = service.create(name, phone, date, time, guests);
        var response = mapper.toCreated(booking);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BookingListResponse read(@RequestParam(required = false) LocalDate date) {
        var bookings = service.get(date);
        var response = mapper.toList(bookings);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookingInfoResponse read(@PathVariable Long id) {
        var booking = service.get(id);
        var response = mapper.toInfo(booking);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public BookingInfoResponse delete(@PathVariable Long id) {
        var booking = service.cancel(id);
        var response = mapper.toInfo(booking);
        return response;
    }
}
