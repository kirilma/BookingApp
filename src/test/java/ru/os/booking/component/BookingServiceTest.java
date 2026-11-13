package ru.os.booking.component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import ru.os.booking.BookingProperties;
import ru.os.booking.entity.Booking;
import ru.os.booking.entity.BookingStatus;
import ru.os.booking.exception.BookingAlreadyCancelledException;
import ru.os.booking.exception.BookingNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository repository;

    private BookingService service;

    @BeforeEach
    void setup() {
        var open = LocalTime.of(12, 0);
        var close = LocalTime.of(22, 0);
        var properties = new BookingProperties(90, 12, 10, open, close);
        var clock = Clock.systemUTC();
        service = new BookingService(repository, properties, clock);
    }

    @Test
    void shouldCancelAndSaveBooking_whenBookingActive() {
        var booking = booking(1, BookingStatus.ACTIVE);
        when(repository.findById(1L)).thenReturn(Optional.of(booking));

        var result = service.cancelBooking(1);

        assertEquals(BookingStatus.CANCELLED, result.getStatus());
        verify(repository).save(result);
    }

    @Test
    void shouldThrowBookingNotFoundException_whenBookingNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> service.cancelBooking(1));

        verify(repository, never()).save(any(Booking.class));
    }

    @Test
    void shouldThrowBookingAlreadyCancelledException_whenBookingCancelled() {
        var booking = booking(1, BookingStatus.CANCELLED);
        when(repository.findById(1L)).thenReturn(Optional.of(booking));

        assertThrows(BookingAlreadyCancelledException.class, () -> service.cancelBooking(1));

        verify(repository, never()).save(any(Booking.class));
    }

    private Booking booking(long id, BookingStatus status) {
        var date = LocalDate.of(2026, 12, 15);
        var time = LocalTime.of(19, 0);
        var booking = new Booking("Anna", "+79991234567", date, time, 4);
        ReflectionTestUtils.setField(booking, "id", id);
        ReflectionTestUtils.setField(booking, "status", status);
        return booking;
    }
}
