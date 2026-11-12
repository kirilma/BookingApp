package ru.os.booking.component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.os.booking.entity.Booking;
import ru.os.booking.exception.BookingNotFoundException;
import ru.os.booking.exception.InvalidBookingDataException;
import ru.os.booking.exception.SlotLimitExceededException;
import ru.os.booking.util.PhoneUtils;

@RequiredArgsConstructor
@Service
public class BookingService {

    private static final int DAY_LIMIT = 90;
    private static final int GUEST_LIMIT = 12;
    private static final int SLOT_LIMIT = 10;

    private final BookingRepository repository;

    @Transactional
    public Booking create(String name, String phone, LocalDate date, LocalTime time, int guests) {
        var errors = new ArrayList<String>();
        var normalized = PhoneUtils.normalize(phone);
        if (normalized == null) {
            errors.add("phone must be +7XXXXXXXXXX or 8XXXXXXXXXX");
        }
        var now = LocalDate.now();
        if (now.isAfter(date) || now.plusDays(DAY_LIMIT).isBefore(date)) {
            var message = "date must be between noew and %s days ahead".formatted(DAY_LIMIT);
            errors.add(message);
        }
        if (LocalTime.now().isAfter(time) || time.getMinute() != 0) {
            errors.add("time must be after now and contain zero minutes");
        }
        if (guests > GUEST_LIMIT) {
            var message = "guest count must be less than %s".formatted(GUEST_LIMIT);
            errors.add(message);
        }
        if (!errors.isEmpty()) {
            throw new InvalidBookingDataException(errors);
        }
        var count = repository.countActiveBookings(date, time);
        if (count >= SLOT_LIMIT) {
            throw new SlotLimitExceededException(SLOT_LIMIT, date, time);
        }
        var booking = new Booking(name, normalized, date, time, guests);
        repository.save(booking);
        return booking;
    }

    @Transactional(readOnly = true)
    public List<Booking> get(LocalDate date) {
        var bookings = date == null ? repository.findAll() : repository.findAllByDate(date);
        return bookings;
    }

    @Transactional(readOnly = true)
    public Booking get(long id) {
        var booking = repository.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
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
