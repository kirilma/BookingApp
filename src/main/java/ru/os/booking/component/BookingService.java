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

    private static final int GUEST_LIMIT = 12;
    private static final int SLOT_LIMIT = 10;

    private final BookingRepository repository;

    @Transactional
    public Booking create(String name, String phone, LocalDate date, LocalTime time, int guests) {
        if (guests > GUEST_LIMIT) {
            throw new RuntimeException();
        }
        var count = repository.getActiveBookingCount(date, time);
        if (count >= SLOT_LIMIT) {
            throw new RuntimeException();
        }
        var booking = new Booking(name, phone, date, time, guests);
        repository.save(booking);
        return booking;
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
