package ru.os.booking;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
