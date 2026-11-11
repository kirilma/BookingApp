package ru.os.booking.component;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.os.booking.entity.Booking;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository repository;

    public List<Booking> get() {
        var bookings = repository.findAll();
        return bookings;
    }

    public Booking get(long id) {
        var booking = repository.findById(id).orElseThrow(RuntimeException::new);
        return booking;
    }
}
