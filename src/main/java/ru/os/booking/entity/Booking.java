package ru.os.booking.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "bookings")
@Entity
public class Booking {

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "guests")
    private Integer guests;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    public Booking(String name, String phone, LocalDate date, LocalTime time, Integer guests) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.guests = guests;
        this.status = BookingStatus.ACTIVE;
    }

    public void cancel() {
        if (this.status != BookingStatus.ACTIVE) {
            throw new RuntimeException();
        }
        this.status = BookingStatus.CANCELLED;
    }
}
