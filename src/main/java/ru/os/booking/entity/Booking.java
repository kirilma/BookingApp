package ru.os.booking.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.os.booking.exception.BookingAlreadyCancelledException;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NonNull
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @NonNull
    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;

    @NonNull
    @Column(name = "guests", nullable = false)
    private Integer guests;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void onCreate() {
        this.status = BookingStatus.ACTIVE;
        this.createdAt = Instant.now();
    }

    public void cancel() {
        if (this.status == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException(this.id);
        }
        this.status = BookingStatus.CANCELLED;
    }
}
