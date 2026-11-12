package ru.os.booking;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class BookingConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    OpenAPI openApi() {
        var info = new Info().title("Booking API").version("1.0-SNAPSHOT");
        var openapi = new OpenAPI().info(info);
        return openapi;
    }
}
