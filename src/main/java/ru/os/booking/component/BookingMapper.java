package ru.os.booking.component;

import java.util.List;

import org.mapstruct.Mapper;

import ru.os.booking.entity.Booking;
import ru.os.booking.web.BookingCreatedResponse;
import ru.os.booking.web.BookingInfoResponse;
import ru.os.booking.web.BookingListResponse;

@Mapper
public interface BookingMapper {

    BookingInfoResponse toInfo(Booking booking);

    BookingCreatedResponse toCreated(Booking booking);

    default BookingListResponse toList(List<Booking> bookings) {
        var mapped = bookings.stream().map(this::toInfo).toList();
        var total = mapped.size();
        var response = new BookingListResponse(mapped, total);
        return response;
    }
}
