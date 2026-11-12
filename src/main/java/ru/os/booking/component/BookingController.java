package ru.os.booking.component;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.os.booking.web.BookingCreateRequest;
import ru.os.booking.web.BookingCreatedResponse;
import ru.os.booking.web.BookingInfoResponse;
import ru.os.booking.web.BookingListResponse;

@Tag(name = "Booking", description = "Управление бронированиями")
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
@RestController
public class BookingController {

    private final BookingService service;
    private final BookingMapper mapper;

    @Operation(summary = "Создать бронирование", description = "Создает новое бронирование на указанную дату и время.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Бронирование создано"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content),
        @ApiResponse(responseCode = "409", description = "Временной слот недоступен", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookingCreatedResponse create(@Valid @RequestBody BookingCreateRequest request) {
        var name = request.name();
        var phone = request.phone();
        var date = request.date();
        var time = request.time();
        var guests = request.guests();
        var booking = service.create(name, phone, date, time, guests);
        var response = mapper.toCreated(booking);
        return response;
    }

    @Operation(summary = "Получить бронирования", description = "Возвращает список бронирований на определенную дату или все бронирования.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Бронирования найдены")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BookingListResponse read(@RequestParam(required = false) LocalDate date) {
        var bookings = service.get(date);
        var response = mapper.toList(bookings);
        return response;
    }

    @Operation(summary = "Получить бронирование", description = "Возвращает бронирование по ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Бронирование найдено"),
        @ApiResponse(responseCode = "404", description = "Бронирование не найдено", content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookingInfoResponse read(@PathVariable Long id) {
        var booking = service.get(id);
        var response = mapper.toInfo(booking);
        return response;
    }

    @Operation(summary = "Отменить бронирование", description = "Отменяет бронирование по ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Бронирование отменено"),
        @ApiResponse(responseCode = "404", description = "Бронирование не найдено", content = @Content),
        @ApiResponse(responseCode = "409", description = "Бронирование уже отменено", content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public BookingInfoResponse delete(@PathVariable Long id) {
        var booking = service.cancel(id);
        var response = mapper.toInfo(booking);
        return response;
    }
}
