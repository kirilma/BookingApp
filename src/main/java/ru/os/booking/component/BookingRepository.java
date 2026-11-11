package ru.os.booking.component;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.os.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT COUNT(b)
            FROM Booking b
            WHERE b.date = :date
            AND b.time = :time
            AND b.status = ru.os.booking.entity.BookingStatus.ACTIVE
    """)
    int getActiveBookingCount(@Param("date") LocalDate date, @Param("time") LocalTime time);
}
