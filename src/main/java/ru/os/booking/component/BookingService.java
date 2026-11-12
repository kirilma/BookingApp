package ru.os.booking.component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.os.booking.BookingLimits;
import ru.os.booking.entity.Booking;
import ru.os.booking.exception.BookingNotFoundException;
import ru.os.booking.exception.InvalidBookingDataException;
import ru.os.booking.exception.SlotLimitExceededException;
import ru.os.booking.util.PhoneUtils;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository repository;
    private final BookingLimits limit;
    private final Clock clock;

    @Transactional
    public Booking createBooking(String name, String phone, LocalDate date, LocalTime time, int guests) {
        var normalized = PhoneUtils.normalize(phone);
        var now = LocalDateTime.now(clock);

        var errors = new ArrayList<String>();
        if (normalized == null) {
            errors.add("phone must be +7XXXXXXXXXX or 8XXXXXXXXXX");
        }
        if (now.toLocalDate().isAfter(date) || now.toLocalDate().plusDays(limit.days()).isBefore(date)) {
            var message = "date must be between now and %s days ahead".formatted(limit.days());
            errors.add(message);
        }
        if (now.toLocalDate().isEqual(date) && now.toLocalTime().isAfter(time)) {
            errors.add("time must be after now");
        }
        if (time.getMinute() != 0 || time.getSecond() != 0) {
            errors.add("time must contain zero minutes and seconds");
        }
        if (limit.open().isAfter(time) || limit.closed().isBefore(time)) {
            var message = "time must be between %s and %s".formatted(limit.open(), limit.closed());
            errors.add(message);
        }
        if (limit.guests() < guests) {
            var message = "guest count must be less than %s".formatted(limit.guests());
            errors.add(message);
        }
        if (!errors.isEmpty()) {
            throw new InvalidBookingDataException(errors);
        }

        var count = repository.countActiveBookings(date, time);
        if (limit.bookings() <= count) {
            throw new SlotLimitExceededException(limit.bookings(), date, time);
        }
        var booking = new Booking(name, normalized, date, time, guests);
        repository.save(booking);
        return booking;
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookings(LocalDate date) {
        var bookings = date == null ? repository.findAllOrdered() : repository.findAllByDateOrdered(date);
        return bookings;
    }

    @Transactional(readOnly = true)
    public Booking getBooking(long id) {
        var booking = repository.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
        return booking;
    }

    @Transactional
    public Booking cancelBooking(long id) {
        var booking = getBooking(id);
        booking.cancel();
        repository.save(booking);
        return booking;
    }
}
