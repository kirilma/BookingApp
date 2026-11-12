package ru.os.booking.web;

import java.util.List;

import org.mapstruct.Mapper;

import ru.os.booking.entity.Booking;

@Mapper
public interface BookingResponseMapper {

    BookingInfoResponse toInfo(Booking booking);

    default BookingListResponse toList(List<Booking> bookings) {
        var mapped = bookings.stream().map(this::toInfo).toList();
        var total = mapped.size();
        var response = new BookingListResponse(mapped, total);
        return response;
    }
}
