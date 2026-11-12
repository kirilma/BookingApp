package ru.os.booking;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class BookingConfiguration {

    @Bean
    Clock clock() {
        var zone = ZoneId.of("Europe/Moscow");
        return Clock.system(zone);
    }

    @Bean
    OpenAPI openApi() {
        var info = new Info().title("Booking API").version("1.0-SNAPSHOT");
        var openApi = new OpenAPI().info(info);
        return openApi;
    }
}
