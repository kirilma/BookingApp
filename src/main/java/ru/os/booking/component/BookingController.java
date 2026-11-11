package ru.os.booking.component;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@RestController
public class BookingController {

    private final BookingService service;
    private final BookingMapper mapper;
}
