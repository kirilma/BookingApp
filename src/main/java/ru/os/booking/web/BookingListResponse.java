package ru.os.booking.web;

import java.util.List;

public record BookingListResponse(
        List<BookingInfoResponse> items,
        Integer total
) {}
