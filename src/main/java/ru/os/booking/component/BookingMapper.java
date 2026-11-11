package ru.os.booking.component;

import org.mapstruct.Mapper;

import ru.os.booking.entity.Booking;
import ru.os.booking.web.BookingInfoResponse;

@Mapper
public interface BookingMapper {

    BookingInfoResponse map(Booking booking);
}
