package ru.os.booking.component;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.os.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
