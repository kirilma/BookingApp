package ru.os.booking.component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.os.booking.entity.Booking;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository repository;

    @Transactional
    public Booking create(String name, String phone, LocalDate date, LocalTime time, Integer guests) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Booking> get() {
        var bookings = repository.findAll();
        return bookings;
    }

    @Transactional(readOnly = true)
    public Booking get(long id) {
        var booking = repository.findById(id).orElseThrow(RuntimeException::new);
        return booking;
    }

    @Transactional 
    public Booking cancel(long id) {
        var booking = get(id);
        booking.cancel();
        repository.save(booking);
        return booking;
    }
}
