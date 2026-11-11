package ru.os.booking.component;

import org.springframework.data.repository.CrudRepository;

import ru.os.booking.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {}
